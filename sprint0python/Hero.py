from random import randrange


class Hero:
    def __init__(self,name,attack,defense,health,max_health):
        """
        En esta clase se recogen los atributos de nuestro jugador (El heroe),
        con el que nos enfrentaremos a los retos del juego y lucharemos por
        llegar al final (con vida).

        Esta clase heroe se conforma por los siguientes atributos:

            name: Nombre elegido por el usuario para el heroe
            attack: Rango de puntos de daño que le quitaremos al enemigo cada vez que invoquemos
                    el metodo "attacking". Los puntos de daño seran aleatorios dentro del rango
                    20-35 con paso 3.
            defense: Rango de puntos de defensa aleatorios dentro del rango 10-35 con paso 5
            health: Representa la salud actual del heroe
            max_health: Representa la salud maxima que puede alcanzar el heroe

        """
        self.name=name
        self.attack=randrange(20,35,3)
        self.defense=randrange(10,35,5)
        self.health=100
        self.max_health=100

    def attacking(self, monster):
        """
        Metodo que realiza un ataque del heroe a un objeto e la clase Monster.
        """
        print("Hero attacks ", monster.name)
        damage=self.attack-monster.defense

        if damage > 0:
            print(f"Enemy {monster.name} has received {damage} damage points.")
            monster.health=monster.health-damage
        else:
            print(f"{monster.name} has blocked the attack.")

    def heal(self):
        """
        Metodo que, segun la vida del heroe, le cura
        """
        if self.health<self.max_health:
            print(f"{self.name} is hurt!")
            if self.health>81:
                self.health=self.max_health
                print(f"{self.name} has healed. Current health: {self.health}")
            else:
                self.health+=20
                print(f"{self.name} has healed. Current health: {self.health}")
        else:
            print(f"{self.name} is in one piece!")

    def defense(self):
        """
        Metodo para aumentar la defensa del heroe
        """
        self.defense+=5
        print(f"{self.name} defends himself. Defense has temporarily increased to {self.defense}.")

    def reset_defense(self):
        """
        Metodo para resetear la defensa del heroe anteriormente aumentada
        """
        self.defense-=5
        print(f"{self.name} defense has reset.")

    def alive(self):
        """
        Metodo que nos dice la vida actual del heroe (si este esta vivo) o por otro lado si
        el heroe ha muerto nos devuelve un booleano = false
        """
        return self.health<0


