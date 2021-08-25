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

package org.beangle.serializer.csv

import org.beangle.commons.activation.{MediaType, MediaTypes}
import org.beangle.commons.collection.page.Page
import org.beangle.commons.lang.reflect.BeanInfos
import org.beangle.serializer.text.AbstractSerializer
import org.beangle.serializer.text.io.StreamWriter
import org.beangle.serializer.text.mapper.{DefaultMapper, Mapper}
import org.beangle.serializer.text.marshal.{DefaultMarshallerRegistry, MarshallerRegistry, MarshallingContext}

object CsvSerializer {
  def apply(): CsvSerializer = {
    val mapper = new DefaultMapper
    val registry = new DefaultMarshallerRegistry(mapper)
    new CsvSerializer(new DefaultCsvDriver(), mapper, registry)
  }
}

final class CsvSerializer(val driver: CsvDriver, val mapper: Mapper, val registry: MarshallerRegistry)
  extends AbstractSerializer {

  override def mediaTypes: Seq[MediaType] = {
    List(MediaTypes.TextCsv)
  }

  override def serialize(item: Any, writer: StreamWriter, params: Map[String, Any]): Unit = {
    val datas = item match {
      case null => null
      case page: Page[_] => page.items
      case _ => item
    }

    val context = new MarshallingContext(this, writer, registry, params)
    writer.start(context)
    if (datas == null) {
      writer.startNode(mapper.serializedClass(classOf[Null]), classOf[Null])
    } else {
      writer.startNode(mapper.serializedClass(datas.getClass), datas.getClass)
      context.marshal(datas, null)
    }
    writer.endNode()
    writer.end(context)
  }

  override def marshalNull(obj: Any, property: String, context: MarshallingContext): Unit = {
    val size = context.getProperties(BeanInfos.get(obj.getClass).properties(property).clazz).size
    if (size > 0) {
      (0 until size) foreach (_ => context.writer.setValue(""))
    } else {
      context.writer.setValue("")
    }
  }

  override def hierarchical: Boolean = {
    false
  }
}
