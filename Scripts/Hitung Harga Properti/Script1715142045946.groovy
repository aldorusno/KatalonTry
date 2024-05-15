import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.keyword.internal.WebUIAbstractKeyword
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

'Test Normal - Perhitungan Harga Properti Maksimal'

//Ambil Data Excel
String pathData = 'Data Files/Data Keuangan/../../Desktop/Data Keuangan.xlsx'

//Map<String, Object> data = new HashedMap()
String penghasilan = findTestData(pathData).getValue('Penghasilan', 1)
String pengeluaran = findTestData(pathData).getValue('Pengeluaran', 1)
String waktu = findTestData(pathData).getValue('Waktu', 1)

//Buka Website
WebUI.openBrowser('')
WebUI.navigateToUrl('https://www.btnproperti.co.id/tools/hitung-harga-properti')

'Berhasil Membuka Halaman Website BTN Properti'
WebUI.takeFullPageScreenshot()

//Input Data
WebUI.setText(findTestObject('input_Penghasilan'), penghasilan)
WebUI.setText(findTestObject('input_Pengeluaran'), pengeluaran)
WebUI.selectOptionByValue(findTestObject('Object Repository/select_Jangka Waktu'), waktu, true)

'Input Penghasilan, Pengeluaran, dan Jangka Waktu'
WebUI.takeFullPageScreenshot()

'Klik Button Hitung'
WebUI.click(findTestObject('btn_Hitung'))

//Get Hasil dari Web
String hasil=WebUI.getText(findTestObject('hasil_Hitung'))
Integer hasilHitung=hasil.replaceAll("\\D", "").toInteger()
println(hasilHitung)

//Convert to Integer
Integer penghasilanInt=penghasilan.toInteger()
Integer pengeluaranInt=pengeluaran.toInteger()
Integer waktuInt=waktu.toInteger()

//Hitung dengan Rumus
Integer hitung = (penghasilanInt-pengeluaranInt)*(waktuInt*12)/3
println(hitung)

//Bandingkan hasil Web dengan Rumus
if (hasilHitung==hitung) {
	println("Hasil Sama")
} else {
		println("Hasil Beda")
	}
WebUI.takeFullPageScreenshot()

'Test Abnormal - Pengeluaran Lebih Besar dari Penghasilan'

//Map<String, Object> data = new HashedMap()
String penghasilan2 = findTestData(pathData).getValue('Penghasilan', 2)
String pengeluaran2 = findTestData(pathData).getValue('Pengeluaran', 2)
String waktu2 = findTestData(pathData).getValue('Waktu', 2)

// Buka Website
WebUI.navigateToUrl('https://www.btnproperti.co.id/tools/hitung-harga-properti')

'Berhasil Membuka Halaman Website BTN Properti'
WebUI.takeFullPageScreenshot()

// Input Data
WebUI.setText(findTestObject('input_Penghasilan'), penghasilan2)
WebUI.setText(findTestObject('input_Pengeluaran'), pengeluaran2)
WebUI.selectOptionByValue(findTestObject('Object Repository/select_Jangka Waktu'), waktu2, true)

'Input Penghasilan, Pengeluaran, dan Jangka Waktu'
WebUI.takeFullPageScreenshot()

//Verify Button Hitung Tidak Dapat Diklik
'Button Hitung Tidak Dapat Diklik'
WebUI.verifyElementNotClickable(findTestObject('Object Repository/btn_Hitung'))
WebUI.takeFullPageScreenshot()

//CloseBrowser
WebUI.closeBrowser()