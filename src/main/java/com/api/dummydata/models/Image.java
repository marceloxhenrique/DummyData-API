package com.api.dummydata.models;

import lombok.Data;

@Data
public class Image {
	private int id;
	private int userId;
	private String url;
	private int width;
	private int height;
	private String title;
}
