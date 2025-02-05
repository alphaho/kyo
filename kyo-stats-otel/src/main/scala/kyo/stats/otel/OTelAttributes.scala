package kyo.stats.otel

import io.opentelemetry.api.common.AttributesBuilder
import io.opentelemetry.api.common.{Attributes => OAttributes}
import kyo.stats.Attributes.Attribute._
import kyo.stats.Attributes.Attribute
import kyo.stats.Attributes

import scala.jdk.CollectionConverters._

object OTelAttributes {

  def apply(a: Attributes): OAttributes = {

    def loop(l: List[Attribute], b: AttributesBuilder): AttributesBuilder =
      l match {
        case Nil => b
        case head :: tail =>
          head match {
            case BooleanListAttribute(name, value) =>
              loop(tail, b.put(name, value: _*))
            case BooleanAttribute(name, value) =>
              loop(tail, b.put(name, value))
            case DoubleListAttribute(name, value) =>
              loop(tail, b.put(name, value: _*))
            case DoubleAttribute(name, value) =>
              loop(tail, b.put(name, value))
            case LongListAttribute(name, value) =>
              loop(tail, b.put(name, value: _*))
            case LongAttribute(name, value) =>
              loop(tail, b.put(name, value))
            case StringListAttribute(name, value) =>
              loop(tail, b.put(name, value: _*))
            case StringAttribute(name, value) =>
              loop(tail, b.put(name, value))
          }
      }
    a.get match {
      case Nil => OAttributes.empty()
      case l   => loop(l, OAttributes.builder()).build()
    }
  }
}
