package tracing

import caliban.execution.FieldInfo
import caliban.wrappers.Wrapper
import caliban.wrappers.Wrapper._
import zio.query.ZQuery
import zio.telemetry.opentracing._

object TracingWrapper {
  def apply(): Wrapper[OpenTracing] =
    CombinedWrapper(List(overallWrapper, parserWrapper, validationWrapper, executionWrapper, fieldWrapper))

  val overallWrapper: Wrapper[OpenTracing] =
    OverallWrapper[OpenTracing](process => request => process(request).root("Query Processing"))

  val parserWrapper: Wrapper[OpenTracing] =
    ParsingWrapper[OpenTracing](process => request => process(request).span("Parsing"))

  val validationWrapper: Wrapper[OpenTracing] =
    ValidationWrapper[OpenTracing](process => request => process(request).span("Validation"))

  val executionWrapper: Wrapper[OpenTracing] =
    ExecutionWrapper[OpenTracing](process => request => process(request).span("Execution"))

  val fieldWrapper: Wrapper[OpenTracing] =
    FieldWrapper[OpenTracing] {
      case (query, info) =>
        val operation = s"Field ${pathToString(info)}"
        ZQuery.fromEffect(query.run.span(s"Field $operation"))
//        for {
//          service <- ZQuery.access[OpenTracing](_.get)
//          old     <- ZQuery.fromEffect(service.currentSpan.get)
//          child   <- ZQuery.fromEffect(service.span(old, operation))
//          old     <- ZQuery.fromEffect(service.currentSpan.get)
//          r <- (ZQuery.fromEffect(service.currentSpan.set(child)) *>
//                query.foldCauseM(
//                  cause =>
//                    ZQuery.fromEffect(service.error(child, cause, tagError = true, logError = true)) *>
//                      ZQuery.halt(cause),
//                  ZQuery.succeed(_)
//                )).ensuring(
//                service.finish(child) *> service.currentSpan.set(old)
//              )
//        } yield r
    }

  def pathToString(path: FieldInfo): String =
    (Left(path.name) :: path.path).reverse.map {
      case Left(s)  => s
      case Right(i) => i.toString
    }.mkString("/")
}
