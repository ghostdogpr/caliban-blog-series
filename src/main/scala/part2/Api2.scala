package part2

import part2.Data._
import zio.UIO

object Api2 {

  case class QueryArgs(count: Int)

  case class Query(orders: QueryArgs => UIO[List[OrderView]])

  case class OrderView(id: OrderId, customer: UIO[Customer], products: List[ProductOrderView])

  case class ProductOrderView(id: ProductId, details: UIO[ProductDetailsView], quantity: Int)

  case class ProductDetailsView(name: String, description: String, brand: UIO[Brand])

  def resolver(dbService: DBService): Query = {

    def getOrders(count: Int): UIO[List[OrderView]] =
      dbService
        .getLastOrders(count)
        .map(_.map(order => OrderView(order.id, dbService.getCustomer(order.customerId), getProducts(order.products))))

    def getProducts(products: List[(ProductId, Int)]): List[ProductOrderView] =
      products.map {
        case (productId, quantity) =>
          ProductOrderView(
            productId,
            dbService
              .getProduct(productId)
              .map(p => ProductDetailsView(p.name, p.description, dbService.getBrand(p.brandId))),
            quantity
          )
      }

    Query(args => getOrders(args.count))
  }

}
