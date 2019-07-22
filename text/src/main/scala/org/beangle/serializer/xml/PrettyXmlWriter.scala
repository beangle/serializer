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
package org.beangle.serializer.xml

import java.io.Writer

import org.beangle.serializer.text.io.{AbstractWriter, StreamException}
import org.beangle.serializer.text.marshal.MarshallingContext

object PrettyXmlWriter {
  val NULL: Array[Char] = "&#x0;".toCharArray
  val AMP: Array[Char] = "&amp;".toCharArray
  val LT: Array[Char] = "&lt;".toCharArray
  val GT: Array[Char] = "&gt;".toCharArray
  val CR: Array[Char] = "&#xd;".toCharArray
  val QUOT: Array[Char] = "&quot;".toCharArray
  val APOS: Array[Char] = "&apos;".toCharArray
  val CLOSE: Array[Char] = "</".toCharArray
}

class PrettyXmlWriter(writer: Writer, lineIndenter: Array[Char], newLine: Array[Char]) extends AbstractWriter {

  import PrettyXmlWriter._

  def this(writer: Writer) {
    this(writer, Array(' ', ' '), Array('\n'))
  }

  private var tagInProgress: Boolean = _
  private var readyForNewLine: Boolean = _
  private var tagIsEmpty: Boolean = _

  override def startNode(name: String, clazz: Class[_]): Unit = {
    val depth = pathStack.size
    tagIsEmpty = false
    finishTag(depth)
    writer.write('<')
    writer.write(name)
    pathStack.push(name, clazz)
    tagInProgress = true
    readyForNewLine = true
    tagIsEmpty = true
  }

  override def addAttribute(key: String, value: String): Unit = {
    writer.write(' ')
    writer.write(key)
    writer.write('=')
    writer.write('\"')
    writeAttributeValue(value)
    writer.write('\"')
  }

  override def setValue(text: String): Unit = {
    readyForNewLine = false
    tagIsEmpty = false
    finishTag(pathStack.size)
    writeText(text)
  }

  override def endNode(): Unit = {
    val name = pathStack.pop().name
    val depth = pathStack.size
    if (tagIsEmpty) {
      writer.write('/')
      readyForNewLine = false
      finishTag(depth)
    } else {
      finishTag(depth)
      writer.write(CLOSE)
      writer.write(name)
      writer.write('>')
    }
    readyForNewLine = true
    if (depth == 0) writer.flush()
  }

  override def flush(): Unit = {
    writer.flush()
  }

  override def close(): Unit = {
    writer.close()
  }

  protected def writeAttributeValue(text: String): Unit = {
    writeText(text, true)
  }

  protected def writeText(text: String): Unit = {
    writeText(text, false)
  }

  private def writerChar(c: Char): Unit = {
    if (c <= '\u001f' || c > '\ud7ff' && c < '\ue000' || c >= '\ufffe')
      throw new StreamException("Invalid character 0x" + Integer.toHexString(c) + " in XML 1.0 stream")
    if (Character.isDefined(c) && !Character.isISOControl(c)) {
      this.writer.write(c)
    } else {
      this.writer.write("&#x")
      this.writer.write(Integer.toHexString(c))
      this.writer.write(';')
    }
  }

  private def writeText(text: String, isAttribute: Boolean): Unit = {
    val length = text.length()
    (0 until length) foreach { i =>
      val c = text.charAt(i)
      c match {
        case '&' => this.writer.write(AMP)
        case '<' => this.writer.write(LT)
        case '>' => this.writer.write(GT)
        case '"' => this.writer.write(QUOT)
        case '\'' => this.writer.write(APOS)
        case '\r' => this.writer.write(CR)
        case '\t' | '\n' => if (!isAttribute) this.writer.write(c) else writerChar(c)
        case _ => writerChar(c)
      }
    }
  }

  private def finishTag(depth: Int): Unit = {
    if (tagInProgress) {
      writer.write('>')
      tagInProgress = false
    }
    if (readyForNewLine) {
      indentNewLine(depth)
      readyForNewLine = false
    }
    tagIsEmpty = false
  }

  protected def indentNewLine(depth: Int): Unit = {
    writer.write(newLine)
    (0 until depth) foreach (_ => writer.write(lineIndenter))
  }

  override def start(context: MarshallingContext): Unit = {
    writer.write("<?xml version=\"1.0\"?>\n")
  }

  override def end(context: MarshallingContext): Unit = {

  }
}
