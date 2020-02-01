package part2

import part2.Data._
import zio.UIO

object Api1 {

  case class QueryArgs(count: Int)
  case class Query(orders: QueryArgs => UIO[List[OrderView]])

  case class OrderView(id: OrderId, customer: Customer, products: List[ProductOrderView])
  case class ProductOrderView(id: ProductId, details: ProductDetailsView, quantity: Int)
  case class ProductDetailsView(name: String, description: String, brand: Brand)

  def resolver(dbService: DBService): Query = {

    def getOrders(count: Int): UIO[List[OrderView]] =
      dbService
        .getLastOrders(count)
        .flatMap(
          UIO.foreach(_)(
            order =>
              for {
                customer <- dbService.getCustomer(order.customerId)
                products <- getProducts(order.products)
              } yield OrderView(order.id, customer, products)
          )
        )

    def getProducts(products: List[(ProductId, Int)]): UIO[List[ProductOrderView]] =
      UIO.foreach(products) {
        case (productId, quantity) =>
          dbService
            .getProduct(productId)
            .flatMap(
              product =>
                dbService
                  .getBrand(product.brandId)
                  .map(brand => ProductDetailsView(product.name, product.description, brand))
            )
            .map(details => ProductOrderView(productId, details, quantity))
      }

    Query(args => getOrders(args.count))
  }

}
