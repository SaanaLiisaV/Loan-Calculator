package com.example.loancalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.loancalculator.ui.theme.LoanCalculatorTheme
import androidx.compose.material3.TextField
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.res.stringResource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Alignment
import androidx.compose.material3.Slider

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LoanCalculatorTheme {
                enableEdgeToEdge()
                Column(modifier = Modifier.padding(16.dp))
                {ReactiveLoanValueUI()}
            }
        }
    }
}


@Composable
fun ReactiveLoanValueUI() {

    val loanInfoText = stringResource(R.string.loan_info)
    val loanAmountText = stringResource(R.string.loan_amount)
    val interestRateText = stringResource(R.string.interest_rate)
    val loanDurationText = stringResource(R.string.loan_duration)

    val resultsText = stringResource(R.string.results)
    val totalIntrestsText = stringResource(R.string.payable_interest)
    val totalOwedText= stringResource(R.string.payable_total)


    // user input values
    var sliderPosition by remember { mutableStateOf(0f)}
    var stringValueLoanAmount by remember { mutableStateOf("0")}
    var stringValueLoanDuration by remember { mutableStateOf("0")}
    var interestRateLabel by remember { mutableStateOf("0")}
    var resultText by remember { mutableStateOf("")}

    // Kun arvo muuttuu, päädytään tänne:
    fun updateValues(){

        // interest slider laber updating
        interestRateLabel = "$interestRateText ${sliderPosition.toInt()}"

        //loan interest
        var loanInterestRate = sliderPosition.toInt()

        //principal amount
        var loanAmount = stringValueLoanAmount.toDoubleOrNull() ?: 0.0

        var loanDuration = stringValueLoanDuration.toIntOrNull() ?: 0

        var totalInterest = loanAmount * (loanInterestRate/100f)*loanDuration/12f
        // total amount owed
        var totalOwed = loanAmount + totalInterest


        //Muotoillaan käyttäjälle sopivaan muotoon:
        resultText = "$loanInfoText\n"
        resultText += "$loanAmountText $loanAmount\n"
        resultText += "$interestRateText $loanInterestRate%\n"
        resultText += "$loanDurationText $loanDuration \n\n"

        resultText += "$resultsText\n"
        resultText += "$totalIntrestsText ${"%.2f".format(totalInterest)}\n"
        resultText += "$totalOwedText ${"%.2f".format(totalOwed)} \n"

    }

    Spacer(modifier=Modifier.fillMaxSize(0.2f))

    // Calculated results for the loan are displayed here
    Text(text=resultText)
    Spacer(modifier=Modifier.fillMaxSize(0.2f))

    // Loan amount input
    TextField(
        value = stringValueLoanAmount,
        onValueChange = {
            stringValueLoanAmount = it
            updateValues() //tämä kutsuu funktiota, joka muuttaa arvon
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        //label = { Text(stringResource(R.string.loan_amount)) },
        label = {
            Text("${stringResource(R.string.loan_amount)}:")
        },
        modifier=Modifier.fillMaxWidth()
    )

    Spacer(modifier=Modifier.fillMaxSize(0.2f))

    // Loan amount input
    TextField(
        value = stringValueLoanDuration,
        onValueChange = {
            stringValueLoanDuration = it
            updateValues() //tämä kutsuu funktiota, joka muuttaa arvon
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        label = {
            Text("${stringResource(R.string.loan_duration)}:")
        },
        modifier=Modifier.fillMaxWidth()
    )

    Spacer(modifier=Modifier.fillMaxSize(0.2f))

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center,
    )
    {Text (text=interestRateLabel)}

    // loan interest slider-input
    Slider(
        value=sliderPosition,
        onValueChange = {
            sliderPosition = it
            updateValues() //tämä kutsuu funktiota
        },
        valueRange= 0f..100.0f,
        steps = 100
    )
}

@Preview(showBackground = true)
@Composable
fun LoanCalcularorPreview() {
    LoanCalculatorTheme {
        ReactiveLoanValueUI()
    }
}
@Preview(locale = "fi")
@Composable
fun PreviewFinnish() {
    ReactiveLoanValueUI()
}
@Preview(locale = "en")
@Composable
fun PreviewEnglish() {
    ReactiveLoanValueUI()
}