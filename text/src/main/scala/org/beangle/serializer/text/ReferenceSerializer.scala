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

import org.beangle.commons.lang.Strings
import org.beangle.serializer.text.io.Path
import org.beangle.serializer.text.marshal.{Id, Marshaller, MarshallingContext}

abstract class ReferenceSerializer extends AbstractSerializer {

  override def marshal(item: Any, marshaller: Marshaller[Any], context: MarshallingContext): Unit = {
    val writer = context.writer
    if (marshaller.targetType.scalar) {
      // strings, ints, dates, etc... don't bother using references.
      marshaller.marshal(item, writer, context)
    } else {
      val currentPath = writer.currentPath
      val objectItem = item.asInstanceOf[AnyRef]
      val existed = context.references.get(objectItem)
      if (existed != null) {
        val referAttrName = mapper.aliasForSystemAttribute("reference")
        if (referAttrName != null) writer.addAttribute(referAttrName, createReference(currentPath, existed.key, context))
      } else {
        val newReferKey = fireReference(currentPath, item, context)
        context.references.put(objectItem, new Id(newReferKey, currentPath))
        marshaller.marshal(item, writer, context)
      }
    }
  }

  protected def createReference(currentPath: Path, existedKey: Object, context: MarshallingContext): String

  protected def fireReference(currentPath: Path, item: Any, context: MarshallingContext): Object
}

abstract class ReferenceByIdSerializer extends ReferenceSerializer {

  protected override def createReference(currentPath: Path, existedKey: Object, context: MarshallingContext): String = {
    existedKey.toString
  }

  protected override def fireReference(currentPath: Path, item: Any, context: MarshallingContext): AnyRef = {
    val key = Strings.unCamel(item.getClass.getSimpleName) + "_" + System.identityHashCode(item)
    val attributeName = mapper.aliasForSystemAttribute("id")
    if (attributeName != null) context.writer.addAttribute(attributeName, key.toString)
    key
  }

}

abstract class ReferenceByXPathSerializer(val absolutePath: Boolean, val singleNode: Boolean) extends ReferenceSerializer {

  protected override def createReference(currentPath: Path, existedKey: Object, context: MarshallingContext): String = {
    val existingPath = existedKey.asInstanceOf[Path]
    val referencePath = if (absolutePath) existingPath else currentPath.relativeTo(existingPath)
    if (singleNode) referencePath.explicit else referencePath.toString
  }

  protected override def fireReference(currentPath: Path, item: Any, context: MarshallingContext): AnyRef = {
    currentPath
  }
}
