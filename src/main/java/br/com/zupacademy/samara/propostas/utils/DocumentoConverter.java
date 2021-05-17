package br.com.zupacademy.samara.propostas.utils;

import javax.persistence.AttributeConverter;
import org.springframework.security.crypto.encrypt.Encryptors;

public class DocumentoConverter implements AttributeConverter<String,String> {
    @Override
    public String convertToDatabaseColumn(String attribute) {
        return Encryptors.queryableText("${proposta.criptografia.secret}", "59486632").encrypt(attribute);
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        return Encryptors.queryableText("${proposta.criptografia.secret}", "59486632").decrypt(dbData);
    }
}
