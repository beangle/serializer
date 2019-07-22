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
package org.beangle.serializer.fst

import java.io.{InputStream, OutputStream}

import org.beangle.commons.io.BinarySerializer
import org.nustaq.serialization.FSTConfiguration

/**
 * @author chaostone
 */
class FSTSerializer extends BinarySerializer {

  var conf: FSTConfiguration = FSTConfiguration.createDefaultConfiguration()

  conf.registerClass(classOf[Some[_]])
  conf.registerClass(None.getClass)

  override def serialize(data: Any, os: OutputStream, params: Map[String, Any]): Unit = {
    // this code cannot work,why?
    //    val oo = conf.getObjectOutput(os)
    //    oo.writeObject(data, data.getClass)
    //    oo.flush()
    os.write(conf.asByteArray(data))
  }

  override def registerClass(clazz: Class[_]): Unit = {
    conf.registerClass(clazz)
  }

  override def deserialize[T](clazz: Class[T], is: InputStream, params: Map[String, Any]): T = {
    val rs = conf.getObjectInput(is).readObject().asInstanceOf[T]
    is.close()
    rs
  }

  override def asBytes(data: Any): Array[Byte] = {
    conf.asByteArray(data)
  }

  override def asObject[T](clazz: Class[T], data: Array[Byte]): T = {
    conf.asObject(data).asInstanceOf[T]
  }

}
