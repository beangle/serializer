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

package org.beangle.serializer.protobuf

import org.beangle.commons.activation.{MediaType, MediaTypes}
import org.beangle.commons.io.{AbstractBinarySerializer, ObjectSerializer}
import org.beangle.commons.lang.reflect.Reflections

class ProtobufSerializer extends AbstractBinarySerializer {

  override def mediaTypes: Seq[MediaType] = {
    MediaTypes.parse("application/x-protobuf")
  }

  override def registerClass(clazz: Class[_]): Unit = {
    register(clazz, Reflections.newInstance[ObjectSerializer](clazz.getName + "ProtobufSerializer"))
  }

}
