package org.beangle.serializer.protobuf

import org.beangle.commons.io.ObjectSerializer
import java.io.InputStream
import java.io.OutputStream

class AccountProtobufSerializer extends ObjectSerializer {

  def serialize(data: Any, os: OutputStream, params: Map[String, Any]): Unit = {
    val account = data.asInstanceOf[Account]
    val builder = Protobuf.Account.newBuilder()
    builder.setName(account.name)
    builder.setDescription(account.description)
    builder.setStatus(account.status)
    account.remoteToken foreach { t =>
      builder.setRemoteToken(t)
    }
    if (null != account.authorities) builder.setAuthorities(account.authorities)
    if (null != account.permissions) builder.setPermissions(account.permissions)
    account.details foreach {
      case (k, v) =>
        builder.putDetails(k, v)
    }

    builder.build().writeTo(os)
  }

  def deserialize(is: InputStream, params: Map[String, Any]): Any = {
    val pa = Protobuf.Account.parseFrom(is)
    val account = new Account(pa.getName, pa.getDescription)
    account.status = pa.getStatus
    account.authorities = pa.getAuthorities
    account.permissions = pa.getPermissions
    account.remoteToken = Option(pa.getRemoteToken)
    val dk = pa.getDetailsMap.entrySet().iterator()
    while (dk.hasNext()) {
      val entry = dk.next()
      account.details += (entry.getKey -> entry.getValue)
    }
    account
  }
}