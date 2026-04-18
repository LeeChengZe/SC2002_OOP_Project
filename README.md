# UML Class Diagram
<img width="14346" height="6368" alt="Group 2 UML Class Diagram (1)" src="https://github.com/user-attachments/assets/98659e88-372a-49eb-98e1-bff0f6c45590" />

# UML Sequence Diagram
<img width="13343" height="5273" alt="Group 2 UML Sequence Diagram (1)" src="https://github.com/user-attachments/assets/ada1a134-df0b-426e-89bb-5930d507a025" />

# Turn-Based Combat Arena Game
A command-line turn-based combat game developed in Java, where players battle enemies using strategic actions, items, and special abilities.
This project demonstrates Object-Oriented Programming concepts such as encapsulation, inheritance, polymorphism, and abstraction.

# Features
- Turn-based combat system
- Multiple character classes (e.g., Warrior, Assassin)
- Status effects (e.g., buffs, debuffs, invisibility)
- Inventory and item usage system
- Battle log for tracking actions
- Extendable design using OOP principles

# Object-Oriented Design
- Encapsulation: Game state and logic are managed within classes
- Inheritance: Different character classes extend a base Combatant class
- Polymorphism: Actions and effects behave differently depending on context
- Abstraction: Interfaces like Action define common behaviors

# How to Run
1. Clone the repository
2. Compile the project:
   javac *.java
3. Run the game:
   java Main

# Gameplay
- Player selects actions each turn:
  - Attack
  - Use Item
  - Defend
- Enemies will respond automatically
- The game continues until either the player or enemy is defeated
