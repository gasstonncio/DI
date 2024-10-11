class Monster:
    def __init__(self,name,attack,defense,health):

        self.name=name
        self.attack=attack
        self.defense=defense
        self.health=health

    def attacking(self, hero):

        print(f"The {self.name} attacks {hero.name}.")
        damage = self.attack - hero.defense
        if damage > 0:
            print(f"The {self.name} has hurt {hero.name} and knocked off {damage} health points.")
            hero.health -= damage
        else:
            print(f"{hero.name} has blocked the attack from the {self.name}!")

    def alive(self):
        return self.health>0



