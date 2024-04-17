package com.amaseng.myinvois.models;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Invoice {
    private String id;
    private Date issueDateTime;
    private String invoiceTypeCode;
    private String documentCurrencyCode;
    private Period invoicePeriod;
    private DocumentReference billingReference;
    private DocumentReference[] additionalDocumentReference;
    private AccountingParty accountingSupplierParty;

    public Invoice(String id, Date issueDateTime, String invoiceTypeCode, String documentCurrencyCode, Period invoicePeriod, DocumentReference billingReference, DocumentReference[] additionalDocumentReference, AccountingParty accountingSupplierParty) {
        this.id = id;
        this.issueDateTime = issueDateTime;
        this.invoiceTypeCode = invoiceTypeCode;
        this.documentCurrencyCode = documentCurrencyCode;
        this.invoicePeriod = invoicePeriod;
        this.billingReference = billingReference;
        this.additionalDocumentReference = additionalDocumentReference;
        this.accountingSupplierParty = accountingSupplierParty;
    }

    public String getId() {
        return id;
    }

    public Date getIssueDateTime() {
        return issueDateTime;
    }

    public String getInvoiceTypeCode() {
        return invoiceTypeCode;
    }

    public String getDocumentCurrencyCode() {
        return documentCurrencyCode;
    }

    public Period getInvoicePeriod() {
        return invoicePeriod;
    }

    public DocumentReference getBillingReference() {
        return billingReference;
    }

    public DocumentReference[] getAdditionalDocumentReference() {
        return additionalDocumentReference;
    }

    public AccountingParty getAccountingSupplierParty() {
        return accountingSupplierParty;
    }

    public Map<Object, Object> toMap() {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss'Z'");
        return new HashMap<Object, Object>() {{
            put("ID", new ArrayList<Object>() {{ add(new HashMap<Object, Object>() {{ put("_", id); }}); }});
            put("IssueDate", new ArrayList<Object>() {{ add(new HashMap<Object, Object>() {{ put("_", dateFormatter.format(issueDateTime)); }}); }});
            put("IssueTime", new ArrayList<Object>() {{ add(new HashMap<Object, Object>() {{ put("_", timeFormatter.format(issueDateTime)); }}); }});
            put("InvoiceTypeCode", new ArrayList<Object>() {{ add(new HashMap<Object, Object>() {{ put("_", invoiceTypeCode); put("listVersionID", "1.0"); }}); }});
            put("DocumentCurrencyCode", new ArrayList<Object>() {{ add(new HashMap<Object, Object>() {{ put("_", documentCurrencyCode); }}); }});
            put("InvoicePeriod", new ArrayList<Object>() {{ add(invoicePeriod.toMap()); }});
            put("BillingReference", new ArrayList<Object>() {{ add(new HashMap<Object, Object>() {{ put("AdditionalDocumentReference", new ArrayList<Object>() {{ add(billingReference.toMap()); }}); }}); }});
            put("AdditionalDocumentReference", new ArrayList<Object>() {{
                for (DocumentReference documentReference : additionalDocumentReference) {
                    add(documentReference.toMap());
                }
            }});
            put("AccountingSupplierParty", new ArrayList<Object>() {{ add(accountingSupplierParty.toMap()); }});
        }};
    }
}
