/*
 * Beangle, Agile Development Scaffold and Toolkits.
 *
 * Copyright © 2005, The Beangle Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.serializer.text

import org.beangle.serializer.text.marshal.{ Marshaller, MarshallerRegistry }
import org.beangle.serializer.text.io.StreamWriter
import org.beangle.serializer.text.mapper.Mapper
import org.beangle.serializer.text.marshal.{ Marshaller, MarshallingContext }
import org.beangle.commons.conversion.ConverterRegistry
import org.beangle.serializer.text.io.StreamDriver
import java.io.StringWriter
import java.io.Writer
import java.io.OutputStream
import scala.collection.immutable.TreeMap
import org.beangle.serializer.text.marshal.Id
import org.beangle.commons.lang.Strings

abstract class AbstractSerializer extends StreamSerializer {

  def driver: StreamDriver
  def mapper: Mapper
  def registry: MarshallerRegistry

  def alias(alias: String, clazz: Class[_]): Unit = {
    mapper.alias(alias, clazz)
  }

  def alias(alias: String, className: String): Unit = {
    mapper.alias(alias, className)
  }

  override def serialize(obj: Any, out: OutputStream, params: Map[String, Any]): Unit = {
    val writer = driver.createWriter(out,params)
    try {
      serialize(obj, writer, params)
    } finally {
      writer.flush()
    }
  }

  def serialize(obj: Any): String = {
    val writer = new StringWriter()
    serialize(obj, writer, Map.empty[String, Any])
    writer.toString()
  }

  def serialize(obj: Any, params: Map[String, Any]): String = {
    val writer = new StringWriter()
    serialize(obj, writer, params)
    writer.toString()
  }

  def serialize(obj: Any, out: Writer, params: Map[String, Any]) {
    val writer = driver.createWriter(out, params)
    try {
      serialize(obj, writer, params)
    } finally {
      writer.flush()
    }
  }

  override def serialize(item: Any, writer: StreamWriter, params: Map[String, Any]): Unit = {
    val context = new MarshallingContext(this, writer, registry, params)
    writer.start(context)
    if (item == null) {
      writer.startNode(mapper.serializedClass(classOf[Null]), classOf[Null])
    } else {
      writer.startNode(mapper.serializedClass(item.getClass()), item.getClass())
      context.marshal(item, null)
    }
    writer.endNode()
    writer.end(context)
  }

  override def marshal(item: Any, marshaller: Marshaller[Any], context: MarshallingContext): Unit = {
    val writer = context.writer
    if (marshaller.targetType.scalar) {
      // strings, ints, dates, etc... don't bother using references.
      marshaller.marshal(item, writer, context)
    } else {
      val objectItem = item.asInstanceOf[AnyRef]
      if (context.currents.contains(objectItem)) {
        val key = Strings.unCamel(item.getClass().getSimpleName) + "_" + System.identityHashCode(item)
        val attributeName = mapper.aliasForSystemAttribute("id")
        if (attributeName != null) context.writer.addAttribute(attributeName, key.toString())
      } else {
        context.currents += objectItem
        marshaller.marshal(item, writer, context)
        context.currents -= objectItem
      }
    }
  }

  override def marshalNull(obj: Any, property: String, context: MarshallingContext): Unit = {

  }

  override def hierarchical: Boolean = {
    true
  }
}
