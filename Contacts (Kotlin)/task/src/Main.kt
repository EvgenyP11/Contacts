package contacts

import kotlinx.datetime.*

open class ActionOnContact {
    var telNumber: String = "[no data]"
        private set(value) {
            field = if (checkPhoneNumber(value) || value == "+(phone)" || value == "+(another)") value
            else "[no number]"
        }
    var timeCreated = "[no data]"
    var timeLastEdit = "[no data]"
    var isPerson = false

    private fun getDateAndTime(): String {
        val currentInstant = Clock.System.now()
        val tz = TimeZone.currentSystemDefault()
        return currentInstant.toLocalDateTime(tz).toString().substringBefore('.')
    }
    fun addTelNumber() {
        print("Enter the number: ")
        telNumber = readln()
    }
    private fun checkPhoneNumber(number: String): Boolean {
        val regex = "[+]?[-|\\s]?(\\d{1,3})?[-|\\s]?([(\\dA-Za-z)]{3,5})?(([-|\\s]?[\\dA-Za-z]{2,4})+)?".toRegex()//"[+]?\\d?[-|\\s]?[(\\dA-Za-z)]{3,5}([-|\\s]?[\\dA-Za-z]{2,4})+".toRegex()
        return regex.matches(number)
    }
    private fun hasNumber(contact: ActionOnContact): Boolean {
        return contact.telNumber != "[no number]"
    }
    fun countContacts(listContacts: MutableList<ActionOnContact>) {
        if (listContacts.isEmpty()) println("The Phone Book has 0 records.")
        else println("The Phone Book has ${listContacts.size} records.")
    }
    fun listContacts(listContacts: MutableList<ActionOnContact>) {
        if (listContacts.isEmpty()) println("The Phone Book has 0 records.")
        else listContacts.forEach {
            print("${listContacts.indexOf(it) + 1}. ")
            println(it.toString())
        }
    }
    fun removeContact(listContacts: MutableList<ActionOnContact>) {
        if (listContacts.isNotEmpty()){
            println("Select a record:")
            listContacts.removeAt(readLine()!!.toInt() - 1)
        } else {
            println("No records to remove!")
            return
        }
    }
    fun editContact(listContacts: MutableList<ActionOnContact>) {
        listContacts(listContacts)
        if (listContacts.isNotEmpty()){
            print("Select a record: ")
            val contact = listContacts[readLine()!!.toInt() - 1]
            if (contact is ContactHuman) {
                print("Select a field (name, surname, birth, gender, number): ")
                when(readln()) {
                    "name" -> contact.addName()
                    "surname" -> contact.addSurname()
                    "birth" -> contact.addBirth()
                    "gender" -> contact.addGender()
                    "number" -> contact.addTelNumber()
                }
                contact.timeLastEdit = getDateAndTime()
                println("The record updated!")
            }
            if (contact is ContactCompany) {
                print("Select a field (address, number): ")
                when(readln()) {
                    "address" -> contact.addAddress()
                    "number" -> contact.addTelNumber()
                }
                contact.timeLastEdit = getDateAndTime()
            }
            println("The record updated!")
        } else {
            println("No records to edit!")
            return
        }
    }
    fun addContactHuman(person: ContactHuman, listContacts: MutableList<ActionOnContact>) {
        person.addName()
        person.addSurname()
        person.addBirth()
        person.addGender()
        person.addTelNumber()
        person.timeCreated = getDateAndTime()
        person.timeLastEdit = getDateAndTime()
        person.isPerson = true
        listContacts.add(person)
        if (hasNumber(person)) println("The record added")
        else {
            println("Wrong number format!")
            println("The record added.")
        }
    }
    fun addContactCompany(company: ContactCompany, listContacts: MutableList<ActionOnContact>) {
        company.addNameCompany()
        company.addAddress()
        company.addTelNumber()
        company.timeCreated = getDateAndTime()
        company.timeLastEdit = getDateAndTime()
        company.isPerson = false
        listContacts.add(company)
        if (hasNumber(company)) println("The record added")
        else {
            println("Wrong number format!")
            println("The record added.")
        }
    }
    fun printHumanOrCompany(index: Int, listContacts: MutableList<ActionOnContact>) {
        if (listContacts[index] is ContactHuman) {
            printHuman(listContacts[index] as ContactHuman)
        }
        if (listContacts[index] is ContactCompany) {
            printCompany(listContacts[index] as ContactCompany)
        }
    }

    private fun printHuman(person: ContactHuman) {
        println("Name: ${person.firstName}")
        println("Surname: ${person.secondName}")
        println("Birth date: ${person.dateOfBirth}")
        println("Gender: ${person.genderOfPerson}")
        println("Number: ${person.telNumber}")
        println("Time created: ${person.timeCreated}")
        println("Time last edit: ${person.timeLastEdit}")
    }
    private fun printCompany(company: ContactCompany) {
        println("Organization name: ${company.nameCompany}")
        println("Address: ${company.addressCompany}")
        println("Number: ${company.telNumber}")
        println("Time created: ${company.timeCreated}")
        println("Time last edit: ${company.timeLastEdit}")
    }
}

class ContactHuman: ActionOnContact() {

    var firstName: String = "[no data]"
        set(value) {
            val letter = value.first().toString()
            field = letter.uppercase() + value.removePrefix(letter)
        }
    var secondName: String = "[no data]"
        set(value) {
            val letter = value.first().toString()
            field = letter.uppercase() + value.removePrefix(letter)
        }
    var genderOfPerson = "[no data]"
    var dateOfBirth = "[no data]"




    override fun toString(): String {
        return "$firstName $secondName"
    }

    fun addName() {
        print("Enter the name of the person: ")
        firstName = readln()
    }
    fun addSurname() {
        print("Enter the surname of the person: ")
        secondName = readln()
    }
    fun addGender() {
        print("Enter the gender (M, F): ")
        val input = readln()
        genderOfPerson = input.ifEmpty {
            println("Bad gender!")
            "[no data]"
        }
    }
    fun addBirth() {
        print("Enter the birth date: ")
        val input = readln()
        dateOfBirth = input.ifEmpty {
            println("Bad birth date!")
            "[no data]"
        }
    }


}

class ContactCompany: ActionOnContact() {
    var nameCompany = "no data"

    var addressCompany = "no data"

    override fun toString(): String {
        return nameCompany
    }

    fun addNameCompany() {
        print("Enter the organization name: ")
        nameCompany = readln()
    }
    fun addAddress() {
        print("Enter the address: ")
        addressCompany = readln()
    }

}

fun main() {
    val notebookContact = mutableListOf<ActionOnContact>()
    while (true) {
        val action = ActionOnContact()
        val person = ContactHuman()
        val company = ContactCompany()
        println()
        print("Enter action (add, remove, edit, count, info, exit): ")
        when(readln()) {
            "exit" -> break
            "add" -> {
                print("Enter the type (person, organization): ")
                when(readln()) {
                    "person" -> action.addContactHuman(person, notebookContact)
                    "organization" -> action.addContactCompany(company, notebookContact)
                }
            }
            "remove" -> action.removeContact(notebookContact)
            "edit" -> action.editContact(notebookContact)
            "count" -> action.countContacts(notebookContact)
            "info" -> {
                action.listContacts(notebookContact)
                print("Enter index to show info: ")
                val index = readln().toInt() - 1
                action.printHumanOrCompany(index, notebookContact)
            }
        }
    }
}

