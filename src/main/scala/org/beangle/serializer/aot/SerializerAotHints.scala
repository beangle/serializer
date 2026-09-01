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

package org.beangle.serializer.aot

import org.beangle.commons.aot.{AotHintRegistrar, AotPolicy}

/** beangle-serializer 的 GraalVM native-image 反射提示。
 *
 * 注册范围：
 *  - JSON/XML/CSV/Protobuf 序列化驱动与模块；
 *  - JDK 序列化类型（String/Number/Date/java.time 等）与 java.time 序列化代理。
 */
class SerializerAotHints extends AotHintRegistrar {
  override def registering(): Unit = {
    registerSerializerTypes()
    registerSerializationModels()
  }

  private def registerSerializerTypes(): Unit = {
    hints.registerType(
      classOf[org.beangle.serializer.json.DefaultJsonDriver],
      classOf[org.beangle.serializer.json.DefaultJsonpDriver],
      classOf[org.beangle.serializer.json.JsonDriver],
      classOf[org.beangle.serializer.json.JsonSerializer],
      classOf[org.beangle.serializer.json.JsonpSerializer])
    hints.registerType(classOf[org.beangle.serializer.json.DefaultModule.type])
    hints.registerType(classOf[org.beangle.serializer.xml.DefaultModule.type])
    hints.registerType(classOf[org.beangle.serializer.text.io.StreamDriver])
    hints.registerType(classOf[org.beangle.serializer.xml.XmlDriver])
  }

  /** 注册 JDK 序列化涉及的基本类型与 java.time 序列化代理。
   *
   *  Java 序列化（如 caffeine jcache 的 JavaSerializationCopier）对缓存键/值做
   *  序列化往返时，ObjectStreamClass 需要把参与序列化的 JDK 类型也注册进
   *  serialization-config，否则写侧报 "SerializationConstructorAccessor class
   *  not found for java.lang.String"。
   */
  private def registerSerializationModels(): Unit = {
    hints.registerSerializable(
      classOf[java.lang.String], classOf[java.lang.Boolean], classOf[java.lang.Integer],
      classOf[java.lang.Long], classOf[java.lang.Short], classOf[java.lang.Byte],
      classOf[java.lang.Double], classOf[java.lang.Float], classOf[java.lang.Character],
      classOf[java.lang.Number], classOf[java.math.BigDecimal], classOf[java.math.BigInteger],
      classOf[java.util.Date], classOf[java.time.Instant], classOf[java.time.LocalDate],
      classOf[java.time.LocalDateTime], classOf[Array[java.io.Serializable]],
      classOf[java.util.ArrayList[?]], classOf[Array[Object]], classOf[Array[String]],
      classOf[Array[Int]])

    // java.time 序列化代理类：LocalDate/LocalDateTime/Instant 等经 writeReplace
    // 写入 java.time.Ser（包级私有，无法 classOf 引用）。反序列化时
    // ObjectInputStream.resolveClass 经 Class.forName("java.time.Ser") 解析，
    // native 下未注册会报 "ClassNotFoundException: java.time.Ser"。
    try {
      val ser = Class.forName("java.time.Ser")
      hints.registerType(ser, AotPolicy(Set(
        AotPolicy.Category.DeclaredConstructors,
        AotPolicy.Category.PublicMethods,
        AotPolicy.Category.DeclaredMethods)))
      hints.registerSerializable(ser)
    } catch {
      case _: Throwable => ()
    }
  }
}
