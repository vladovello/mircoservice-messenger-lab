package com.messenger.chat.application.query.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class AttachmentSizeDto {
    private float size;
    private String metric;

    public static @NonNull AttachmentSizeDto fromBytes(long bytes) {
        var sizes = FileSizeMetric.getSIZES();
        var low = 0;
        var high = sizes.size() - 1;

        while (low < high - 1) {
            var mid = (low + high) / 2;

            if (sizes.get(mid) <= bytes) {
                low = mid;
            } else {
                high = mid;
            }
        }

        var attachmentSizeDto = new AttachmentSizeDto();
        attachmentSizeDto.setSize((float) Math.round((float) bytes / sizes.get(low) * 10) / 10);
        attachmentSizeDto.setMetric(FileSizeMetric.valueOfLabel(sizes.get(low)).getMetric());

        return attachmentSizeDto;
    }
}