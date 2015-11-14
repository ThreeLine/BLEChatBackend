package com.fnx.db.entity.util;

import org.bson.types.ObjectId;

public class ObjectIdGenerator {
	public static String generate() {
		return ObjectId.get().toString();
	}
}
