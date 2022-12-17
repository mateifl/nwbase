package ro.zizicu.nwbase.transaction;


import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class TransactionStatusConverter implements AttributeConverter<DistributedTransactionStatus, String> {

    @Override
    public String convertToDatabaseColumn(DistributedTransactionStatus status) {
        if (status == null) {
            return null;
        }
        return status.getStatus();
    }

    @Override
    public DistributedTransactionStatus convertToEntityAttribute(String code) {
        if (code == null) {
            return null;
        }

        return Stream.of(DistributedTransactionStatus.values())
                .filter(c -> c.getStatus().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
