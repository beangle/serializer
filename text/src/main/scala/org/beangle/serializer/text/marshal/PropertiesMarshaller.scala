/*
 * Copyright (C) 2005, The Beangle Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.beangle.serializer.text.marshal

import java.{util => ju}

import org.beangle.commons.collection.Properties
import org.beangle.serializer.text.io.StreamWriter
import org.beangle.serializer.text.mapper.Mapper
import org.beangle.serializer.text.marshal.Type

class PropertiesMarshaller(val mapper: Mapper) extends Marshaller[ju.Properties] {

  def marshal(source: ju.Properties, writer: StreamWriter, context: MarshallingContext): Unit = {
    val iter = source.propertyNames()
    while (iter.hasMoreElements) {
      val key = iter.nextElement.asInstanceOf[String]
      val value = extractOption(source.getProperty(key))
      if (null != value) {
        writer.startNode(mapper.serializedMember(source.getClass, key), value.getClass)
        context.marshal(value)
        writer.endNode()
      }
    }
  }

  override def targetType: Type = {
    Type.Object
  }

}

class JsonObjectMarshaller(val mapper: Mapper) extends Marshaller[Properties] {

  def marshal(source: Properties, writer: StreamWriter, context: MarshallingContext): Unit = {
    val iter = source.keys.iterator
    while (iter.hasNext) {
      val key = iter.next()
      val value = extractOption(source(key)).asInstanceOf[AnyRef]
      if (null != value && None != value) {
        writer.startNode(mapper.serializedMember(source.getClass, key), value.getClass)
        context.marshal(value)
        writer.endNode()
      }
    }
  }

  override def targetType: Type = {
    Type.Object
  }

}
