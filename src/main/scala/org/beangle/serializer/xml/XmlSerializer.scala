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

package org.beangle.serializer.xml

import org.beangle.commons.activation.MediaTypes
import org.beangle.serializer.text.{ AbstractSerializer, ReferenceByXPathSerializer }
import org.beangle.serializer.text.mapper.{ DefaultMapper, Mapper }
import org.beangle.serializer.text.marshal.{ DefaultMarshallerRegistry, MarshallerRegistry }

import org.beangle.commons.activation.MediaType

object XmlSerializer {
  def apply(): XmlSerializer = {
    val driver = new DomDriver
    val mapper = new DefaultMapper
    val registry = new DefaultMarshallerRegistry(mapper)
    driver.registry = registry
    new XmlSerializer(driver, mapper, registry)
  }
}

class XmlSerializer(val driver: XmlDriver, val mapper: Mapper, val registry: MarshallerRegistry)
    extends AbstractSerializer {

  override def mediaTypes: Seq[MediaType] = {
    List(MediaTypes.ApplicationXml)
  }

}

class XmlXPathSerializer(val driver: XmlDriver, val mapper: Mapper, val registry: MarshallerRegistry, absolutePath: Boolean, singleNode: Boolean)
    extends ReferenceByXPathSerializer(absolutePath, singleNode) {

  override def mediaTypes: Seq[MediaType] = {
    List(MediaTypes.ApplicationXml)
  }

}
