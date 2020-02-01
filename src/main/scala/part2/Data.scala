package part2

object Data {
  type ProductId  = Int
  type OrderId    = Int
  type CustomerId = Int
  type BrandId    = Int

  case class Brand(id: BrandId, name: String)
  case class Product(id: ProductId, name: String, description: String, brandId: BrandId)
  case class Customer(id: CustomerId, firstName: String, lastName: String)
  case class Order(id: OrderId, customerId: CustomerId, products: List[(ProductId, Int)])
}
