class Monster:
    def __init__(self,name,attack,defense,health):

        self.name=name
        self.attack=attack
        self.defense=defense
        self.health=health

    def attacking(self, hero):

        print(f"The {self.name} attacks {hero.name}.")

        if self.attack<=hero.defense:
            print(f"{hero.name} has blocked the attack from the {self.name}!")
        else:
            print(f"The {self.name} has hurt {hero.name} and knocked off {hero.health-self.attack} health points.")
            hero.health-=self.attack

    def alive(self):
        return self.health>0



