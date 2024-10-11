from Hero import Hero
from Dungeon import Dungeon

def main():
        name_hero=input("Enter your hero's name: ")
        hero=Hero(name_hero)

        dungeon = Dungeon(hero)
        dungeon.play()

if __name__ == "__main__":
        main()