package part2

object Query {
  val orders: String = """
                         |{
                         |  orders(count: 20) {
                         |    id
                         |    customer {
                         |      id
                         |      firstName
                         |      lastName
                         |    }
                         |    products {
                         |      id
                         |      quantity
                         |      details {
                         |        name
                         |        description
                         |      }
                         |    }
                         |  }
                         |}
                         |""".stripMargin
}
