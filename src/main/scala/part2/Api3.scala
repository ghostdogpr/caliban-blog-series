package part2

import part2.Data._
import zquery.{ DataSource, Request, ZQuery }

object Api3 {

  type MyQuery[+A] = ZQuery[Any, Nothing, A]

  case class QueryArgs(count: Int)
  case class Query(orders: QueryArgs => MyQuery[List[OrderView]])

  case class OrderView(id: OrderId, customer: MyQuery[Customer], products: List[ProductOrderView])
  case class ProductOrderView(id: ProductId, details: MyQuery[ProductDetailsView], quantity: Int)
  case class ProductDetailsView(name: String, description: String, brand: MyQuery[Brand])

  def resolver(dbService: DBService): Query = {

    case class GetCustomer(id: CustomerId) extends Request[Nothing, Customer]
    val CustomerDataSource: DataSource[Any, GetCustomer] =
      DataSource.fromFunctionM("CustomerDataSource")(req => dbService.getCustomer(req.id))
    def getCustomer(id: CustomerId): MyQuery[Customer] = ZQuery.fromRequest(GetCustomer(id))(CustomerDataSource)

    case class GetProduct(id: ProductId) extends Request[Nothing, Product]
    val ProductDataSource: DataSource[Any, GetProduct] =
      DataSource.fromFunctionM("ProductDataSource")(req => dbService.getProduct(req.id))
    def getProduct(id: ProductId): MyQuery[Product] = ZQuery.fromRequest(GetProduct(id))(ProductDataSource)

    case class GetBrand(id: BrandId) extends Request[Nothing, Brand]
    val BrandDataSource: DataSource[Any, GetBrand] =
      DataSource.fromFunctionM("BrandDataSource")(req => dbService.getBrand(req.id))
    def getBrand(id: BrandId): MyQuery[Brand] = ZQuery.fromRequest(GetBrand(id))(BrandDataSource)

    def getOrders(count: Int): MyQuery[List[OrderView]] =
      ZQuery
        .fromEffect(dbService.getLastOrders(count))
        .map(_.map(order => OrderView(order.id, getCustomer(order.customerId), getProducts(order.products))))

    def getProducts(products: List[(ProductId, Int)]): List[ProductOrderView] =
      products.map {
        case (productId, quantity) =>
          ProductOrderView(
            productId,
            getProduct(productId).map(p => ProductDetailsView(p.name, p.description, getBrand(p.brandId))),
            quantity
          )
      }

    Query(args => getOrders(args.count))
  }

}
