import tkinter as tk
from recursos import Recursos
from controlador import GameController
from vista import MainMenu, GameView

def main():
    # Crear la ventana principal de Tkinter
    root = tk.Tk()

    # Crear la instancia de Recursos
    recursos = Recursos(root)

    # Crear el controlador de juego
    controlador = GameController(recursos)

    # Crear la vista principal (menú)
    menu = MainMenu(root, controlador.start_game, controlador.show_stats, root.quit)

    # Ejecutar el bucle principal de Tkinter
    root.mainloop()

if __name__ == "__main__":
    main()






