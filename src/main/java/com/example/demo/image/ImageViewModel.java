package com.example.demo.image;

import java.util.List;

public record ImageViewModel(String imageId, String imageLink, Point point, List<Tag> tags) {
}
