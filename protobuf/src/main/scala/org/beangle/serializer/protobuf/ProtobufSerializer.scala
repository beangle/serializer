package org.beangle.serializer.protobuf

import org.beangle.commons.activation.MimeTypes
import org.beangle.commons.io.AbstractBinarySerializer
import org.beangle.commons.lang.ClassLoaders

import javax.activation.MimeType

class ProtobufSerializer extends AbstractBinarySerializer {

  override def mediaTypes: Seq[MimeType] = {
    MimeTypes.parse("application/x-protobuf")
  }

  override def registerClass(clazz: Class[_]): Unit = {
    register(clazz, ClassLoaders.newInstance(clazz.getName + "ProtobufSerializer"))
  }

}
