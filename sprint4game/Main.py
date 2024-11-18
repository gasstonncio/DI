import tkinter as tk
from vista import MainMenu
from controlador import GameController
from recursos import Recursos


def main():
    # Crear la ventana principal
    root = tk.Tk()
    root.geometry("720x480")
    root.title("Juego de Memoria")

    print("Inicializando recursos...")
    recursos = Recursos(root)  # Inicializar los recursos (imágenes)
    print("Recursos inicializados.")

    # Crear el controlador
    print("Inicializando GameController...")
    controlador = GameController(root, recursos)
    print("GameController inicializado.")

    # Crear el menú principal
    print("Inicializando MainMenu...")
    menu = MainMenu(
        root,
        start_game_callback=controlador.start_game,
        show_stats_callback=controlador.show_stats,
        quit_callback=controlador.quit_game  # Actualizamos aquí para que llame al método quit_game del controlador
    )
    print("MainMenu inicializado.")

    # Ejecutar el bucle principal de Tkinter
    print("Iniciando bucle principal de Tkinter...")
    root.mainloop()


if __name__ == "__main__":
    main()