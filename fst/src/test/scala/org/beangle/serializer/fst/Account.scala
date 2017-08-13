package org.beangle.serializer.fst

import java.io.{ Externalizable, ObjectInput, ObjectOutput }
import java.security.Principal

import org.beangle.commons.collection.Collections

final class Account extends Externalizable with Serializable {

  var name: String = _

  var description: String = _

  var remoteToken: Option[String] = None

  var status: Int = _

  var authorities: Set[Long] = _

  var permissions: Set[Long] = _

  var details: Map[String, String] = Map.empty

  def this(name: String, description: String) {
    this()
    this.name = name
    this.description = description
  }

  private def change(value: Boolean, mask: Int): Unit = {
    if (value) status = status | mask
    else {
      if ((status & mask) > 0) status = status ^ mask
    }
  }

  def writeExternal(out: ObjectOutput): Unit = {
    println("write external")
    out.writeObject(name)
    out.writeObject(description)
    out.writeObject(remoteToken.orNull)
    out.writeInt(status)
    if (authorities == null) {
      out.writeInt(0)
    } else {
      out.writeInt(authorities.size)
      authorities.foreach(out.writeLong(_))
    }
    if (permissions == null) {
      out.writeInt(0)
    } else {
      out.writeInt(permissions.size)
      permissions.foreach(out.writeLong(_))
    }
    out.writeInt(details.size)
    details foreach {
      case (k, v) =>
        out.writeObject(k)
        out.writeObject(v)
    }
  }

  def readExternal(in: ObjectInput): Unit = {
    println("read external")
    name = in.readObject.toString
    description = in.readObject.toString
    remoteToken = Option(in.readObject.asInstanceOf[String])
    status = in.readInt()
    val authoritieSize = in.readInt()
    if (authoritieSize > 0) {
      authorities = ((0 until authoritieSize) map (i => in.readLong())).toSet
    }
    val permissionSize = in.readInt()
    if (permissionSize > 0) {
      permissions = ((0 until permissionSize) map (i => in.readLong())).toSet
    }
    val mapSize = in.readInt()
    val temp = Collections.newMap[String, String]
    (0 until mapSize) foreach { i =>
      val k = in.readObject.toString
      val v = in.readObject.toString
      temp += (k -> v)
    }
    details = temp.toMap
  }
}