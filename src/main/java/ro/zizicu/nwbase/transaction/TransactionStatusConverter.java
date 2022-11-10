package ro.zizicu.nwbase.transaction;


import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class TransactionStatusConverter implements AttributeConverter<TransactionStatus, String> {

    @Override
    public String convertToDatabaseColumn(TransactionStatus status) {
        if (status == null) {
            return null;
        }
        return status.getStatus();
    }

    @Override
    public TransactionStatus convertToEntityAttribute(String code) {
        if (code == null) {
            return null;
        }

        return Stream.of(TransactionStatus.values())
                .filter(c -> c.getStatus().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
