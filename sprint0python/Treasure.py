import random
from random import randrange
from Hero import Hero

class Treasure:
    #Clase de tesoros posibles que puede encontrar el heroe tras derrotar a un monstruo

    def gold(self, hero):
        #Metodo que respresenta un tesoro en froma de monedas de oro
        print(f"{hero.name} has found a bag of gold!{self.profit()}")

    @staticmethod
    def profit() ->int:
        #Metodo para devolver una cantidad aleatoria de oro al metodo gold
        return randrange(50,350,50)

    def attack_increase(self, hero):
        #Metodo que representa un tesoro en forma de aumento de daño del heroe
        hero.attack+=10
        print(f"{hero.name} has increased his attacking.")

    def defense_increase(self, hero):
        #Metodo que representa un tesoro en forma de aumento de defensa del heroe
        hero.defense+=10
        print(f"{hero.name} has increased his defense.")

    def healer(self, hero):
        #Metodo que representa un tesoro en forma de curacion de la vida al maximo
        hero.health=hero.max_health
        print(f"{hero.name}")

    def found_treasure(self,hero):
        #Metodo que escoge un tesoro aleatorio

        benefit=[self.gold,self.attack_increase,self.defense_increase,self.healer]
        return random.choice(benefit)(hero)