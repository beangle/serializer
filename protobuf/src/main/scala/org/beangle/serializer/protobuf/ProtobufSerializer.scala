package org.beangle.serializer.protobuf

import org.beangle.commons.io.AbstractBinarySerializer
import java.io.InputStream
import java.io.OutputStream
import org.beangle.commons.lang.ClassLoaders

class ProtobufSerializer extends AbstractBinarySerializer {

  override def registerClass(clazz: Class[_]): Unit = {
    register(clazz, ClassLoaders.newInstance(clazz.getName + "ProtobufSerializer"))
  }
}