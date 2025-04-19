package com.lumens.Service.imp;

import com.lumens.Domain.CupomFiscal;
import com.lumens.Domain.PedidoCompra;
import com.lumens.Repository.CupomFiscalRepository;
import com.lumens.Repository.PedidoCompraRepository;
import com.lumens.Service.ICupomFiscalService;
import com.lumens.exception.PedidoInexistenteException;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
public class CupomFiscalService implements ICupomFiscalService {
    private final CupomFiscalRepository cupomFiscalRepository;
    private final PedidoCompraRepository pedidoCompraRepository;
    private final PedidoCompraService pedidoCompraService;
    public CupomFiscalService(CupomFiscalRepository cupomFiscalRepository, PedidoCompraRepository pedidoCompraRepository, PedidoCompraService pedidoCompraService) {
        this.cupomFiscalRepository = cupomFiscalRepository;
        this.pedidoCompraRepository = pedidoCompraRepository;
        this.pedidoCompraService = pedidoCompraService;
    }

    public CupomFiscal gerarCupom(Long idPedido) {
        PedidoCompra pedido = pedidoCompraRepository.findByIdAndStatusPedido(idPedido, PedidoCompra.Status.ATIVO)
                .orElseThrow(() -> new PedidoInexistenteException("Pedido não encontrado ou não está ATIVO"));

        pedidoCompraService.concluirPedido(idPedido);

        CupomFiscal cupom = new CupomFiscal();
        cupom.setPedidoCompra(pedido);
        cupom.setXmlCupom(formatarXml(gerarXml(pedido)));

        return cupomFiscalRepository.save(cupom);
    }

    private String gerarXml(PedidoCompra pedido) {
        Random random = new Random();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
        String dataEmissao = sdf.format(new Date());
        String chaveAcesso = gerarChaveAcesso();
        String numeroCupom = String.format("%06d", random.nextInt(999999));

        StringBuilder xmlBuilder = new StringBuilder();
        xmlBuilder.append("<CupomFiscal xmlns=\"http://www.portalfiscal.inf.br/nfe\">");
        xmlBuilder.append("<infCupom versao=\"4.00\" Id=\"Cupom").append(pedido.getId()).append("\">");
        xmlBuilder.append("<ide>");
        xmlBuilder.append("<cUF>35</cUF>"); // Código UF (35 = SP)
        xmlBuilder.append("<cNF>").append(numeroCupom).append("</cNF>");
        xmlBuilder.append("<natOp>VENDA DE PRODUTOS</natOp>");
        xmlBuilder.append("<mod>65</mod>"); // Modelo 65 = Cupom Fiscal Eletrônico
        xmlBuilder.append("<serie>1</serie>");
        xmlBuilder.append("<nCupom>").append(numeroCupom).append("</nCupom>");
        xmlBuilder.append("<dhEmi>").append(dataEmissao).append("</dhEmi>");
        xmlBuilder.append("<tpAmb>1</tpAmb>"); // 1 = Produção
        xmlBuilder.append("</ide>");
        xmlBuilder.append("<emit>");
        xmlBuilder.append("<CNPJ>12345678000195</CNPJ>");
        xmlBuilder.append("<xNome>Cafeteria Lumens Ltda</xNome>");
        xmlBuilder.append("<xFant>Lumens Coffee</xFant>");
        xmlBuilder.append("<enderEmit>");
        xmlBuilder.append("<xLgr>Rua das Cafeteiras</xLgr>");
        xmlBuilder.append("<nro>123</nro>");
        xmlBuilder.append("<xBairro>Centro</xBairro>");
        xmlBuilder.append("<cMun>3550308</cMun>"); // Código IBGE São Paulo
        xmlBuilder.append("<xMun>SAO PAULO</xMun>");
        xmlBuilder.append("<UF>SP</UF>");
        xmlBuilder.append("<CEP>01001000</CEP>");
        xmlBuilder.append("</enderEmit>");
        xmlBuilder.append("</emit>");
        xmlBuilder.append("<det>");
        xmlBuilder.append("<prod>");
        xmlBuilder.append("<cProd>").append(pedido.getId()).append("</cProd>");
        xmlBuilder.append("<xProd>Pedido Cafeteria</xProd>");
        xmlBuilder.append("<qCom>1.0000</qCom>");
        xmlBuilder.append("<vUnCom>").append(String.format("%.2f", pedido.getValorTotal())).append("</vUnCom>");
        xmlBuilder.append("<vProd>").append(String.format("%.2f", pedido.getValorTotal())).append("</vProd>");
        xmlBuilder.append("</prod>");
        xmlBuilder.append("</det>");
        xmlBuilder.append("<total>");
        xmlBuilder.append("<vCupom>").append(String.format("%.2f", pedido.getValorTotal())).append("</vCupom>");
        xmlBuilder.append("</total>");
        xmlBuilder.append("<infAdic>");
        xmlBuilder.append("<infCpl>Pedido realizado via sistema interno</infCpl>");
        xmlBuilder.append("</infAdic>");
        xmlBuilder.append("<Signature xmlns=\"http://www.w3.org/2000/09/xmldsig#\">");
        xmlBuilder.append("<SignedInfo>");
        xmlBuilder.append("<CanonicalizationMethod Algorithm=\"http://www.w3.org/TR/2001/REC-xml-c14n-20010315\"/>");
        xmlBuilder.append("<SignatureMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#rsa-sha1\"/>");
        xmlBuilder.append("<Reference URI=\"#Cupom").append(pedido.getId()).append("\">");
        xmlBuilder.append("<DigestMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#sha1\"/>");
        xmlBuilder.append("<DigestValue>").append(UUID.randomUUID().toString().replace("-", "")).append("</DigestValue>");
        xmlBuilder.append("</Reference>");
        xmlBuilder.append("</SignedInfo>");
        xmlBuilder.append("<SignatureValue>").append(UUID.randomUUID().toString().replace("-", "")).append("</SignatureValue>");
        xmlBuilder.append("</Signature>");
        xmlBuilder.append("</infCupom>");
        xmlBuilder.append("</CupomFiscal>");

        return xmlBuilder.toString();
    }

    private String gerarChaveAcesso() {
        Random random = new Random();
        SimpleDateFormat sdf = new SimpleDateFormat("yyMM");
        String data = sdf.format(new Date());

        return String.format("35%d%s%02d12345678901234",
                random.nextInt(9)+1, // Modelo (1-9)
                data, // Ano e mês
                random.nextInt(89)+10); // Número aleatório
    }
    private String formatarXml(String xml) {
        try {
            javax.xml.transform.Transformer transformer =
                    javax.xml.transform.TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(javax.xml.transform.OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            java.io.StringWriter writer = new java.io.StringWriter();
            transformer.transform(
                    new javax.xml.transform.stream.StreamSource(new java.io.StringReader(xml)),
                    new javax.xml.transform.stream.StreamResult(writer)
            );
            return writer.toString();
        } catch (Exception e) {
            return xml; // Retorna o original se falhar a formatação
        }
    }
    public CupomFiscal buscarCupomId(Long id) {
        return cupomFiscalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cupom não encontrado."));
    }
}