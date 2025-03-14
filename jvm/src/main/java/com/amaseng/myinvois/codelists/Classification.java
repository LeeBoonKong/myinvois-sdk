package com.amaseng.myinvois.codelists;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Classification {
    C001("001", "Breastfeeding equipment"),
    C002("002", "Child care centres and kindergartens fees"),
    C003("003", "Computer, smartphone or tablet"),
    C004("004", "Consolidated e-Invoice"),
    C005("005", "Construction materials (as specified under Fourth Schedule of the Lembaga Pembangunan Industri Pembinaan Malaysia Act 1994)"),
    C006("006", "Disbursement"),
    C007("007", "Donation"),
    C008("008", "e-Commerce - e-Invoice to buyer / purchaser"),
    C009("009", "e-Commerce - Self-billed e-Invoice to seller, logistics, etc."),
    C010("010", "Education fees"),
    C011("011", "Goods on consignment (Consignor)"),
    C012("012", "Goods on consignment (Consignee)"),
    C013("013", "Gym membership"),
    C014("014", "Insurance - Education and medical benefits"),
    C015("015", "Insurance - Takaful or life insurance"),
    C016("016", "Interest and financing expenses"),
    C017("017", "Internet subscription"),
    C018("018", "Land and building"),
    C019("019", "Medical examination for learning disabilities and early intervention or rehabilitation treatments of learning disabilities"),
    C020("020", "Medical examination or vaccination expenses"),
    C021("021", "Medical expenses for serious diseases"),
    C022("022", "Others"),
    C023("023", "Petroleum operations (as defined in Petroleum (Income Tax) Act 1967)"),
    C024("024", "Private retirement scheme or deferred annuity scheme"),
    C025("025", "Motor vehicle"),
    C026("026", "Subscription of books / journals / magazines / newspapers / other similar publications"),
    C027("027", "Reimbursement"),
    C028("028", "Rental of motor vehicle"),
    C029("029", "EV charging facilities (Installation, rental, sale / purchase or subscription fees)"),
    C030("030", "Repair and maintenance"),
    C031("031", "Research and development"),
    C032("032", "Foreign income"),
    C033("033", "Self-billed - Betting and gaming"),
    C034("034", "Self-billed - Importation of goods"),
    C035("035", "Self-billed - Importation of services"),
    C036("036", "Self-billed - Others"),
    C037("037", "Self-billed - Monetary payment to agents, dealers or distributors"),
    C038("038", "Sports equipment, rental / entry fees for sports facilities, registration in sports competition or sports training fees imposed by associations / sports clubs / companies registered with the Sports Commissioner or Companies Commission of Malaysia and carrying out sports activities as listed under the Sports Development Act 1997"),
    C039("039", "Supporting equipment for disabled person"),
    C040("040", "Voluntary contribution to approved provident fund"),
    C041("041", "Dental examination or treatment"),
    C042("042", "Fertility treatment"),
    C043("043", "Treatment and home care nursing, daycare centres and residential care centers"),
    C044("044", "Vouchers, gift cards, loyalty points, etc"),
    C045("045", "Self-billed - Non-monetary payment to agents, dealers or distributors");

    private final String code;
    private final String description;
}