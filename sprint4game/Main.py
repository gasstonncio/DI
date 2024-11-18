import tkinter as tk
from controlador import GameController
from recursos import Recursos

def main():
    # Crear la ventana principal
    root = tk.Tk()
    root.title("Juego de Memoria")
    root.geometry("720x480")

    # Crear los recursos (imágenes de las cartas)
    recursos = Recursos()

    # Crear el controlador del juego
    app = GameController(root, recursos)

    # Iniciar el bucle de eventos
    root.mainloop()

if __name__ == "__main__":
    main()



