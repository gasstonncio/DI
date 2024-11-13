import tkinter as tk
from tkinter import simpledialog, Toplevel


class GameView:
    def __init__(self, on_card_click_callback, update_move_count_callback, update_time_callback):
        self.on_card_click_callback = on_card_click_callback
        self.update_move_count_callback = update_move_count_callback
        self.update_time_callback = update_time_callback
        self.labels = []

    def create_board(self, model):
        #Crear la ventana del juego
        self.window = Toplevel()
        self.window.title("Juego de Memoria")

        #Crear el tablero
        size = len(model.board)
        self.labels = []
        for i in range(size):
            row = []
            for j in range(size):
                label = tk.Label(self.window, text="?", width=10, height=3, relief="solid")
                label.grid(row=i, column=j)
                label.bind("<Button-1>", lambda event, x=i, y=j: self.on_card_click(event, x, y))
                row.append(label)
            self.labels.append(row)

        #Etiquetas de movimientos y tiempo
        self.move_label = tk.Label(self.window, text=f"Movimientos: {model.moves}")
        self.move_label.grid(row=size, column=0, columnspan=2)

        self.time_label = tk.Label(self.window, text="Tiempo: 0")
        self.time_label.grid(row=size, column=2, columnspan=2)

    def update_board(self, pos, image_id):
        i, j = pos
        self.labels[i][j].config(text=image_id)

    def reset_cards(self, pos1, pos2):
        i1, j1 = pos1
        i2, j2 = pos2
        self.labels[i1][j1].config(text="?")
        self.labels[i2][j2].config(text="?")

    def update_move_count(self, moves):
        self.move_label.config(text=f"Movimientos: {moves}")

    def update_time(self, time):
        self.time_label.config(text=f"Tiempo: {time}")

    def destroy(self):
        self.window.destroy()


class MainMenu:
    def __init__(self, root, start_game_callback, show_stats_callback, quit_callback):
        self.root = root
        self.start_game_callback = start_game_callback
        self.show_stats_callback = show_stats_callback
        self.quit_callback = quit_callback

        #Ventana del menú
        self.menu_window = tk.Frame(root)
        self.menu_window.pack()

        #Botones
        self.play_button = tk.Button(self.menu_window, text="Jugar", command=self.start_game)
        self.play_button.pack()

        self.stats_button = tk.Button(self.menu_window, text="Estadísticas", command=self.show_stats)
        self.stats_button.pack()

        self.quit_button = tk.Button(self.menu_window, text="Salir", command=self.quit)
        self.quit_button.pack()

    def ask_player_name(self):
        #Solicitar el nombre del jugador
        return simpledialog.askstring("Nombre del jugador", "Por favor ingresa tu nombre:")

    def ask_difficulty(self):
        #Solicitar la dificultad
        return simpledialog.askstring("Dificultad", "Elige una dificultad (facil, medio, dificil):")

    def start_game(self):
        #Obtener nombre y dificultad
        player_name = self.ask_player_name()
        difficulty = self.ask_difficulty()

        #Ejecutar el callback de inicio de juego
        self.start_game_callback(player_name, difficulty)

    def show_stats(self):
        self.show_stats_callback()

    def quit(self):
        self.quit_callback()
