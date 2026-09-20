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

package org.beangle.serializer.text.mapper

import java.io.File
import java.net.{URI, URL}
import java.{lang => jl, util => ju}

import org.beangle.commons.lang.{ClassLoaders, Strings}

import scala.math.BigDecimal

class DefaultMapper extends Mapper {

  val classAlias = new collection.mutable.HashMap[Class[?], String]

  aliasBuildin()

  def alias(alias: String, clazz: Class[?]): Unit = {
    classAlias.put(clazz, alias)
  }

  def aliasUnCamel(classes: Class[?]*): Unit = {
    classes foreach { clazz =>
      classAlias.put(clazz, Strings.unCamel(clazz.getSimpleName))
    }
  }

  def alias(alias: String, className: String): Unit = {
    try {
      classAlias.put(ClassLoaders.load(className), alias)
    } catch {
      case _: Throwable =>
    }
  }

  override def serializedClass(clazz: Class[?]): String = {
    var simpleName = clazz.getSimpleName
    if (simpleName.endsWith("Bean")) simpleName = simpleName.substring(0, simpleName.length() - 4)
    classAlias.getOrElse(clazz, Strings.unCamel(simpleName))
  }

  override def aliasForSystemAttribute(name: String): String = {
    name
  }

  override def serializedMember(clazz: Class[?], memberName: String): String = {
    memberName
  }

  private def aliasBuildin(): Unit = {
    //basic
    alias("int", classOf[jl.Integer])
    alias("char", classOf[jl.Character])
    alias("big-int", classOf[java.math.BigInteger])
    alias("java-class", classOf[Class[?]])
    aliasUnCamel(classOf[Null], classOf[jl.Float], classOf[jl.Double], classOf[jl.Long], classOf[jl.Short])
    aliasUnCamel(classOf[jl.Byte], classOf[jl.Boolean], classOf[jl.Number], classOf[jl.Object], classOf[BigDecimal])
    aliasUnCamel(classOf[jl.StringBuilder], classOf[StringBuffer], classOf[scala.StringBuilder])
    aliasUnCamel(classOf[String], classOf[jl.reflect.Method], classOf[jl.reflect.Constructor[?]], classOf[jl.reflect.Field], classOf[ju.Date])
    aliasUnCamel(classOf[URI], classOf[URL])
    aliasUnCamel(classOf[scala.math.BigInt], classOf[scala.math.BigDecimal])

    //java util
    alias("bit-set", classOf[ju.BitSet])
    alias("map", classOf[ju.Map[?, ?]])
    alias("entry", classOf[ju.Map.Entry[?, ?]])
    alias("properties", classOf[ju.Properties])
    alias("list", classOf[ju.List[?]])
    alias("set", classOf[ju.Set[?]])
    alias("sorted-set", classOf[ju.SortedSet[?]])

    alias("linked-list", classOf[ju.LinkedList[?]])
    alias("vector", classOf[ju.Vector[?]])
    alias("tree-map", classOf[ju.TreeMap[?, ?]])
    alias("tree-set", classOf[ju.TreeSet[?]])
    alias("hashtable", classOf[ju.Hashtable[?, ?]])

    alias("empty-list", ju.Collections.emptyList.getClass)
    alias("empty-map", ju.Collections.emptyMap.getClass)
    alias("empty-set", ju.Collections.emptySet.getClass)
    alias("singleton-list", ju.Collections.singletonList(this).getClass)
    alias("singleton-map", ju.Collections.singletonMap(this, null).getClass)
    alias("singleton-set", ju.Collections.singleton(this).getClass)

    //scala collection
    alias("list", classOf[::[?]])
    alias("list", classOf[collection.Seq[?]])
    alias("set", classOf[collection.Set[?]])
    alias("map", classOf[collection.Map[?, ?]])
    alias("sql-timestamp", "java.sql.Timestamp")
    alias("sql-time", "java.sql.Time")
    alias("sql-date", "java.sql.Date")

    alias("file", classOf[File])
    alias("locale", classOf[ju.Locale])
    alias("gregorian-calendar", classOf[ju.Calendar])

    alias("duration", "javax.xml.datatype.Duration")
    alias("concurrent-hash-map", classOf[ju.concurrent.ConcurrentHashMap[?, ?]])
    alias("enum-set", classOf[ju.EnumSet[?]])
    alias("enum-map", classOf[ju.EnumMap[?, ?]])

    alias("uuid", classOf[ju.UUID])
  }
}
