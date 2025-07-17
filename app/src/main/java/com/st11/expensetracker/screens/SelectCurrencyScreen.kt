package com.st11.expensetracker.screens

import android.app.Activity
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.st11.expensetracker.R
import com.st11.expensetracker.navigation.Screen
import com.st11.expensetracker.utils.DynamicStatusBar
import com.st11.expensetracker.viewmodel.CurrencyViewModel
import org.koin.androidx.compose.koinViewModel


//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun SelectCurrencyScreen(navController: NavController) {
//
//    val currencyViewModel: CurrencyViewModel = koinViewModel()
//
//    val backgroundColor = colorResource(id = R.color.light_green)
//
//    DynamicStatusBar(backgroundColor)  // ✅ Apply dynamic status bar settings
//    val currencyList = listOf(
//        "AED", // United Arab Emirates Dirham
//        "AFN", // Afghan Afghani
//        "ALL", // Albanian Lek
//        "AMD", // Armenian Dram
//        "ANG", // Netherlands Antillean Guilder
//        "AOA", // Angolan Kwanza
//        "ARS", // Argentine Peso
//        "AUD", // Australian Dollar
//        "AWG", // Aruban Florin
//        "AZN", // Azerbaijani Manat
//        "BAM", // Bosnia-Herzegovina Convertible Mark
//        "BBD", // Barbadian Dollar
//        "BDT", // Bangladeshi Taka
//        "BGN", // Bulgarian Lev
//        "BHD", // Bahraini Dinar
//        "BIF", // Burundian Franc
//        "BMD", // Bermudian Dollar
//        "BND", // Brunei Dollar
//        "BOB", // Bolivian Boliviano
//        "BRL", // Brazilian Real
//        "BSD", // Bahamian Dollar
//        "BTN", // Bhutanese Ngultrum
//        "BWP", // Botswana Pula
//        "BYN", // Belarusian Ruble
//        "BZD", // Belize Dollar
//        "CAD", // Canadian Dollar
//        "CDF", // Congolese Franc
//        "CHF", // Swiss Franc
//        "CLP", // Chilean Peso
//        "CNY", // Chinese Yuan
//        "COP", // Colombian Peso
//        "CRC", // Costa Rican Colón
//        "CUP", // Cuban Peso
//        "CVE", // Cape Verdean Escudo
//        "CZK", // Czech Koruna
//        "DJF", // Djiboutian Franc
//        "DKK", // Danish Krone
//        "DOP", // Dominican Peso
//        "DZD", // Algerian Dinar
//        "EGP", // Egyptian Pound
//        "ERN", // Eritrean Nakfa
//        "ETB", // Ethiopian Birr
//        "EUR", // Euro
//        "FJD", // Fijian Dollar
//        "FKP", // Falkland Islands Pound
//        "FOK", // Faroese Króna
//        "GBP", // British Pound Sterling
//        "GEL", // Georgian Lari
//        "GGP", // Guernsey Pound
//        "GHS", // Ghanaian Cedi
//        "GIP", // Gibraltar Pound
//        "GMD", // Gambian Dalasi
//        "GNF", // Guinean Franc
//        "GTQ", // Guatemalan Quetzal
//        "GYD", // Guyanaese Dollar
//        "HKD", // Hong Kong Dollar
//        "HNL", // Honduran Lempira
//        "HRK", // Croatian Kuna (now replaced by EUR in Croatia)
//        "HTG", // Haitian Gourde
//        "HUF", // Hungarian Forint
//        "IDR", // Indonesian Rupiah
//        "ILS", // Israeli New Shekel
//        "IMP", // Isle of Man Pound
//        "INR", // Indian Rupee
//        "IQD", // Iraqi Dinar
//        "IRR", // Iranian Rial
//        "ISK", // Icelandic Króna
//        "JEP", // Jersey Pound
//        "JMD", // Jamaican Dollar
//        "JOD", // Jordanian Dinar
//        "JPY", // Japanese Yen
//        "KES", // Kenyan Shilling (Ksh)
//        "KGS", // Kyrgyzstani Som
//        "KHR", // Cambodian Riel
//        "KID", // Kiribati Dollar
//        "KMF", // Comorian Franc
//        "KRW", // South Korean Won
//        "KWD", // Kuwaiti Dinar
//        "KYD", // Cayman Islands Dollar
//        "KZT", // Kazakhstani Tenge
//        "LAK", // Lao Kip
//        "LBP", // Lebanese Pound
//        "LKR", // Sri Lankan Rupee
//        "LRD", // Liberian Dollar
//        "LSL", // Lesotho Loti
//        "LYD", // Libyan Dinar
//        "MAD", // Moroccan Dirham
//        "MDL", // Moldovan Leu
//        "MGA", // Malagasy Ariary
//        "MKD", // Macedonian Denar
//        "MMK", // Burmese Kyat
//        "MNT", // Mongolian Tögrög
//        "MOP", // Macanese Pataca
//        "MRU", // Mauritanian Ouguiya
//        "MUR", // Mauritian Rupee
//        "MVR", // Maldivian Rufiyaa
//        "MWK", // Malawian Kwacha
//        "MXN", // Mexican Peso
//        "MYR", // Malaysian Ringgit
//        "MZN", // Mozambican Metical
//        "NAD", // Namibian Dollar
//        "NGN", // Nigerian Naira
//        "NIO", // Nicaraguan Córdoba
//        "NOK", // Norwegian Krone
//        "NPR", // Nepalese Rupee
//        "NZD", // New Zealand Dollar
//        "OMR", // Omani Rial
//        "PAB", // Panamanian Balboa
//        "PEN", // Peruvian Sol
//        "PGK", // Papua New Guinean Kina
//        "PHP", // Philippine Peso
//        "PKR", // Pakistani Rupee
//        "PLN", // Polish Złoty
//        "PYG", // Paraguayan Guaraní
//        "QAR", // Qatari Riyal
//        "RON", // Romanian Leu
//        "RSD", // Serbian Dinar
//        "RUB", // Russian Ruble
//        "RWF", // Rwandan Franc
//        "SAR", // Saudi Riyal
//        "SBD", // Solomon Islands Dollar
//        "SCR", // Seychellois Rupee
//        "SDG", // Sudanese Pound
//        "SEK", // Swedish Krona
//        "SGD", // Singapore Dollar
//        "SHP", // Saint Helena Pound
//        "SLE", // Sierra Leonean Leone
//        "SOS", // Somali Shilling
//        "SRD", // Surinamese Dollar
//        "SSP", // South Sudanese Pound
//        "STN", // São Tomé and Príncipe Dobra
//        "SYP", // Syrian Pound
//        "SZL", // Swazi Lilangeni
//        "THB", // Thai Baht
//        "TJS", // Tajikistani Somoni
//        "TMT", // Turkmenistani Manat
//        "TND", // Tunisian Dinar
//        "TOP", // Tongan Paʻanga
//        "TRY", // Turkish Lira
//        "TTD", // Trinidad and Tobago Dollar
//        "TVD", // Tuvaluan Dollar
//        "TWD", // New Taiwan Dollar
//        "TZS", // Tanzanian Shilling
//        "UAH", // Ukrainian Hryvnia
//        "UGX", // Ugandan Shilling
//        "USD", // United States Dollar
//        "UYU", // Uruguayan Peso
//        "UZS", // Uzbekistani Soʻm
//        "VES", // Venezuelan Bolívar
//        "VND", // Vietnamese Đồng
//        "VUV", // Vanuatu Vatu
//        "WST", // Samoan Tālā
//        "XAF", // Central African CFA Franc
//        "XCD", // East Caribbean Dollar
//        "XOF", // West African CFA Franc
//        "XPF", // CFP Franc
//        "YER", // Yemeni Rial
//        "ZAR", // South African Rand
//        "ZMW", // Zambian Kwacha
//        "ZWL"  // Zimbabwean Dollar
//    )
//
//    var selectedCurrency by remember { mutableStateOf("KES") }
//
//    Scaffold(
//        topBar = {
//            CenterAlignedTopAppBar(
//                title = { Text("Select Currency", color = Color.White) },
//                navigationIcon = {
////                    IconButton(onClick = { navController.popBackStack() }) {
////                        Icon(
////                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
////                            contentDescription = "Back",
////                            tint = Color.White
////                        )
////                    }
//                },
//                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
//                    containerColor = backgroundColor
//                )
//            )
//        },
//        containerColor = colorResource(id = R.color.light_bg_color)
//    ) { paddingValues ->
//
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(paddingValues)
//                .padding(16.dp),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Text("Please Choose your preferred currency to Continue", fontSize = 18.sp, fontWeight = FontWeight.Medium)
//
//            Spacer(modifier = Modifier.height(24.dp))
//
//            LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
//                items(currencyList) { currency ->
//                    val isSelected = currency == selectedCurrency
//
//                    Surface(
//                        shape = RoundedCornerShape(24.dp),
//                        color = if (isSelected) Color.White else Color(0xFFE0E0E0),
//                        tonalElevation = 2.dp,
//                        shadowElevation = 2.dp,
//                        border = BorderStroke(1.dp, if (isSelected) backgroundColor else Color.Gray),
//                        modifier = Modifier
//                            .clickable { selectedCurrency = currency }
//                    ) {
//                        Text(
//                            text = currency,
//                            modifier = Modifier
//                                .padding(horizontal = 20.dp, vertical = 10.dp),
//                            color = if (isSelected) backgroundColor else Color.Black,
//                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
//                        )
//                    }
//                }
//            }
//
//            Spacer(modifier = Modifier.height(40.dp))
//
//            Button(
//                onClick = {
//                    // TODO: Save selectedCurrency to DB or prefs
////                    navController.popBackStack()
//                    currencyViewModel.saveUserCurrency(selectedCurrency)
//                    navController.navigate(Screen.Home.route)
//
//                },
//                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.green)),
//                shape = RoundedCornerShape(12.dp),
//                modifier = Modifier
//                    .fillMaxWidth(0.8f)
//                    .height(50.dp)
//            ) {
//                Text("Save", color = Color.White, fontSize = 16.sp)
//            }
//        }
//    }
//}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectCurrencyScreen(navController: NavController) {
    val currencyViewModel: CurrencyViewModel = koinViewModel()
    val backgroundColor = colorResource(id = R.color.light_green)

    DynamicStatusBar(backgroundColor)

    val currencyList = listOf(
                "AED", // United Arab Emirates Dirham
        "AFN", // Afghan Afghani
        "ALL", // Albanian Lek
        "AMD", // Armenian Dram
        "ANG", // Netherlands Antillean Guilder
        "AOA", // Angolan Kwanza
        "ARS", // Argentine Peso
        "AUD", // Australian Dollar
        "AWG", // Aruban Florin
        "AZN", // Azerbaijani Manat
        "BAM", // Bosnia-Herzegovina Convertible Mark
        "BBD", // Barbadian Dollar
        "BDT", // Bangladeshi Taka
        "BGN", // Bulgarian Lev
        "BHD", // Bahraini Dinar
        "BIF", // Burundian Franc
        "BMD", // Bermudian Dollar
        "BND", // Brunei Dollar
        "BOB", // Bolivian Boliviano
        "BRL", // Brazilian Real
        "BSD", // Bahamian Dollar
        "BTN", // Bhutanese Ngultrum
        "BWP", // Botswana Pula
        "BYN", // Belarusian Ruble
        "BZD", // Belize Dollar
        "CAD", // Canadian Dollar
        "CDF", // Congolese Franc
        "CHF", // Swiss Franc
        "CLP", // Chilean Peso
        "CNY", // Chinese Yuan
        "COP", // Colombian Peso
        "CRC", // Costa Rican Colón
        "CUP", // Cuban Peso
        "CVE", // Cape Verdean Escudo
        "CZK", // Czech Koruna
        "DJF", // Djiboutian Franc
        "DKK", // Danish Krone
        "DOP", // Dominican Peso
        "DZD", // Algerian Dinar
        "EGP", // Egyptian Pound
        "ERN", // Eritrean Nakfa
        "ETB", // Ethiopian Birr
        "EUR", // Euro
        "FJD", // Fijian Dollar
        "FKP", // Falkland Islands Pound
        "FOK", // Faroese Króna
        "GBP", // British Pound Sterling
        "GEL", // Georgian Lari
        "GGP", // Guernsey Pound
        "GHS", // Ghanaian Cedi
        "GIP", // Gibraltar Pound
        "GMD", // Gambian Dalasi
        "GNF", // Guinean Franc
        "GTQ", // Guatemalan Quetzal
        "GYD", // Guyanaese Dollar
        "HKD", // Hong Kong Dollar
        "HNL", // Honduran Lempira
        "HRK", // Croatian Kuna (now replaced by EUR in Croatia)
        "HTG", // Haitian Gourde
        "HUF", // Hungarian Forint
        "IDR", // Indonesian Rupiah
        "ILS", // Israeli New Shekel
        "IMP", // Isle of Man Pound
        "INR", // Indian Rupee
        "IQD", // Iraqi Dinar
        "IRR", // Iranian Rial
        "ISK", // Icelandic Króna
        "JEP", // Jersey Pound
        "JMD", // Jamaican Dollar
        "JOD", // Jordanian Dinar
        "JPY", // Japanese Yen
        "KES", // Kenyan Shilling (Ksh)
        "KGS", // Kyrgyzstani Som
        "KHR", // Cambodian Riel
        "KID", // Kiribati Dollar
        "KMF", // Comorian Franc
        "KRW", // South Korean Won
        "KWD", // Kuwaiti Dinar
        "KYD", // Cayman Islands Dollar
        "KZT", // Kazakhstani Tenge
        "LAK", // Lao Kip
        "LBP", // Lebanese Pound
        "LKR", // Sri Lankan Rupee
        "LRD", // Liberian Dollar
        "LSL", // Lesotho Loti
        "LYD", // Libyan Dinar
        "MAD", // Moroccan Dirham
        "MDL", // Moldovan Leu
        "MGA", // Malagasy Ariary
        "MKD", // Macedonian Denar
        "MMK", // Burmese Kyat
        "MNT", // Mongolian Tögrög
        "MOP", // Macanese Pataca
        "MRU", // Mauritanian Ouguiya
        "MUR", // Mauritian Rupee
        "MVR", // Maldivian Rufiyaa
        "MWK", // Malawian Kwacha
        "MXN", // Mexican Peso
        "MYR", // Malaysian Ringgit
        "MZN", // Mozambican Metical
        "NAD", // Namibian Dollar
        "NGN", // Nigerian Naira
        "NIO", // Nicaraguan Córdoba
        "NOK", // Norwegian Krone
        "NPR", // Nepalese Rupee
        "NZD", // New Zealand Dollar
        "OMR", // Omani Rial
        "PAB", // Panamanian Balboa
        "PEN", // Peruvian Sol
        "PGK", // Papua New Guinean Kina
        "PHP", // Philippine Peso
        "PKR", // Pakistani Rupee
        "PLN", // Polish Złoty
        "PYG", // Paraguayan Guaraní
        "QAR", // Qatari Riyal
        "RON", // Romanian Leu
        "RSD", // Serbian Dinar
        "RUB", // Russian Ruble
        "RWF", // Rwandan Franc
        "SAR", // Saudi Riyal
        "SBD", // Solomon Islands Dollar
        "SCR", // Seychellois Rupee
        "SDG", // Sudanese Pound
        "SEK", // Swedish Krona
        "SGD", // Singapore Dollar
        "SHP", // Saint Helena Pound
        "SLE", // Sierra Leonean Leone
        "SOS", // Somali Shilling
        "SRD", // Surinamese Dollar
        "SSP", // South Sudanese Pound
        "STN", // São Tomé and Príncipe Dobra
        "SYP", // Syrian Pound
        "SZL", // Swazi Lilangeni
        "THB", // Thai Baht
        "TJS", // Tajikistani Somoni
        "TMT", // Turkmenistani Manat
        "TND", // Tunisian Dinar
        "TOP", // Tongan Paʻanga
        "TRY", // Turkish Lira
        "TTD", // Trinidad and Tobago Dollar
        "TVD", // Tuvaluan Dollar
        "TWD", // New Taiwan Dollar
        "TZS", // Tanzanian Shilling
        "UAH", // Ukrainian Hryvnia
        "UGX", // Ugandan Shilling
        "USD", // United States Dollar
        "UYU", // Uruguayan Peso
        "UZS", // Uzbekistani Soʻm
        "VES", // Venezuelan Bolívar
        "VND", // Vietnamese Đồng
        "VUV", // Vanuatu Vatu
        "WST", // Samoan Tālā
        "XAF", // Central African CFA Franc
        "XCD", // East Caribbean Dollar
        "XOF", // West African CFA Franc
        "XPF", // CFP Franc
        "YER", // Yemeni Rial
        "ZAR", // South African Rand
        "ZMW", // Zambian Kwacha
        "ZWL"  // Zimbabwean Dollar
    )

    var selectedCurrency by remember { mutableStateOf("KES") }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Select Currency", color = Color.White) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = backgroundColor
                )
            )
        },
        containerColor = colorResource(id = R.color.light_bg_color)
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Please choose your preferred currency to continue",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(24.dp))

            // ✅ Vertical List with KES at top
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                val listState = rememberLazyListState(
                    initialFirstVisibleItemIndex = currencyList.indexOf("KES")
                )

                LazyColumn(
                    state = listState,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(currencyList.size) { index ->
                        val currency = currencyList[index]
                        val isSelected = currency == selectedCurrency

                        val scale by animateFloatAsState(if (isSelected) 1.15f else 1.0f)
                        val alpha by animateFloatAsState(if (isSelected) 1f else 0.6f)

                        Text(
                            text = currency,
                            fontSize = if (isSelected) 22.sp else 18.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            color = if (isSelected) backgroundColor else Color.DarkGray,
                            modifier = Modifier
                                .graphicsLayer(
                                    scaleX = scale,
                                    scaleY = scale,
                                    alpha = alpha
                                )
                                .clickable {
                                    selectedCurrency = currency
                                }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = {
                    currencyViewModel.saveUserCurrency(selectedCurrency)
                    navController.navigate(Screen.Home.route)
                },
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.green)),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(50.dp)
            ) {
                Text("Save and Continue", color = Color.White, fontSize = 16.sp)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SelectCurrencyScreenPreview() {
    SelectCurrencyScreen(navController = rememberNavController())
}
