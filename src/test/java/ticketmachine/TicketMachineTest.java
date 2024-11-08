package ticketmachine;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

class TicketMachineTest {
	private static final int PRICE = 50; // Une constante

	private TicketMachine machine; // l'objet à tester

	@BeforeEach
	public void setUp() {
		machine = new TicketMachine(PRICE); // On initialise l'objet à tester
	}

	@Test
	// On vérifie que le prix affiché correspond au paramètre passé lors de
	// l'initialisation
	// S1 : le prix affiché correspond à l’initialisation.
	void priceIsCorrectlyInitialized() {
		// Paramètres : valeur attendue, valeur effective, message si erreur
		assertEquals(PRICE, machine.getPrice(), "Initialisation incorrecte du prix");
	}

	@Test
	// S2 : la balance change quand on insère de l’argent
	void insertMoneyChangesBalance() {
		machine.insertMoney(10);
		machine.insertMoney(20);
		// Les montants ont été correctement additionnés
		assertEquals(10 + 20, machine.getBalance(), "La balance n'est pas correctement mise à jour");
	}

	@Test
	void exceptionThrownForInvalidTicketPrice() {
		assertThrows(IllegalArgumentException.class, () -> new TicketMachine(-1), "Ticket price must be positive");
	}

	@Test
	void exceptionThrownForNonPositiveInsertAmount() {
		assertThrows(IllegalArgumentException.class, () -> machine.insertMoney(-10), "Inserted amount must be positive");
		assertThrows(IllegalArgumentException.class, () -> machine.insertMoney(0), "Inserted amount must be positive");
	}

	@Test
	void printTicketReducesBalanceWhenEnoughMoney() {
		machine.insertMoney(50);
		assertTrue(machine.printTicket(), "Ticket should be printed when balance is sufficient");
		assertEquals(0, machine.getBalance(), "Balance should be zero after ticket is printed");
	}

	@Test
	void printTicketFailsWhenNotEnoughMoney() {
		machine.insertMoney(30);
		assertFalse(machine.printTicket(), "Ticket should not be printed when balance is insufficient");
		assertEquals(30, machine.getBalance(), "Balance should remain unchanged when ticket is not printed");
	}

	@Test
	void refundReturnsBalanceAndResetsToZero() {
		machine.insertMoney(40);
		assertEquals(40, machine.refund(), "Refund should return the correct balance");
		assertEquals(0, machine.getBalance(), "Balance should be zero after refund");
	}

	@Test
	void totalIsUpdatedAfterTicketIsPrinted() {
		machine.insertMoney(50);
		machine.printTicket();
		assertEquals(PRICE, machine.getTotal(), "Total should be updated after printing a ticket");
	}

	@Test
	void totalRemainsUnchangedIfTicketNotPrinted() {
		machine.insertMoney(30);
		machine.printTicket();
		assertEquals(0, machine.getTotal(), "Total should not change if ticket is not printed");
	}
}
