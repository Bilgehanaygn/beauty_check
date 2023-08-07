package com.example.demo.image;

import java.util.List;

public record ImageRateCommand(String point, List<String> tags) {
}
