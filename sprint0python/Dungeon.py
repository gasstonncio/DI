import random
from Treasure import Treasure
from Monster import Monster
class Dungeon:
    def __init__(self, hero):
        self.hero = hero  # El heroe que esta explorando la mazmorras
        self.monsters = self.generate_monsters()  # Genera una lista de monstruos
        self.treasure = Treasure()  # Instancia de la clase Treasure

    @staticmethod
    def generate_monsters():
        # Genera una lista de monstruos con atributos aleatorios
        monster_list = [
            Monster("Atomic skull", random.randrange(15, 25, 5), random.randrange(5, 15, 5), 50),
            Monster("Dehydrated zombie", random.randrange(20, 30, 5), random.randrange(10, 20, 5), 70),
            Monster("Lame spider", random.randrange(25, 35, 5), random.randrange(15, 25, 5), 90),
            Monster("Gypsy vampire", random.randrange(30, 40, 5), random.randrange(10, 20, 5), 60),
            Monster("Bald werewolf", random.randrange(40, 50, 5), random.randrange(20, 30, 5), 120)
        ]
        return monster_list

    def play(self):
        print("The hero enters the dungeon.")
        while self.monsters and self.hero.alive:
            current_monster = random.choice(self.monsters)  # Escoge un monstruo aleatorio
            print(f"You have encountered a {current_monster.name}.")
            self.face_monster(current_monster)

        if self.hero.alive:
            print(f"¡{self.hero.name} has defeated all the monsters and conquered the dungeon!")
        else:
            print("Hero has been defeated in the dungeon.")

    def face_monster(self, enemy):
        while enemy.alive() and self.hero.alive():
            print("What do you want to do?")
            print("1. Attack")
            print("2. Defend")
            print("3. Heal")
            action = input("Choose an option: ")

            if action == "1":
                self.hero.attacking(enemy)  # Heroe ataca al monstruo
                if enemy.alive():  # Si el monstruo aun esta vivo, ataca al heroe
                    enemy.attacking(self.hero)
            elif action == "2":
                self.hero.defense()  # Heroe se defiende
                enemy.attacking(self.hero)  # Monstruo ataca al heroe
                self.hero.reset_defense()  # Resetea defensa
            elif action == "3":
                self.hero.heal()  # Heroe se cura
                enemy.attacking(self.hero)  # Monstruo ataca al heroe
            else:
                print("Invalid option.")

        if not enemy.alive():  # Si el enemigo fue derrotado
            print(f"You have defeated {enemy.name}.")
            print("Looking for a treasure...")
            self.treasure.found_treasure(self.hero)
            self.monsters.remove(enemy)  # Elimina al monstruo derrotado de la lista
        elif not self.hero.alive():  # Si el héroe muere
            print(f"You have been defeated by {enemy.name}.")

