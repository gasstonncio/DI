import tkinter as tk
from tkinter import messagebox
from vista import GameView
from modelo import GameModel


class GameController:
    def __init__(self, root, recursos):
        self.root = root
        self.recursos = recursos
        self.game_model = GameModel()
        self.game_view = None

    def start_game(self, player_name, difficulty):
        """Inicia el juego con el nombre del jugador y la dificultad elegida."""
        self.game_model.setup_game(player_name, difficulty)

        # Crear una nueva ventana para el juego
        game_window = tk.Toplevel(self.root)
        game_window.title(f"Juego de Memoria - {player_name}")

        self.game_view = GameView(game_window, self.game_model, self.on_card_click, self.recursos)
        self.game_view.update_board()
        self.game_view.update_move_count(self.game_model.move_count)

        # Iniciar el temporizador
        self.game_view.start_timer()

    def show_stats(self):
        """Muestra las estadísticas del juego."""
        stats_list = self.game_model.load_stats()
        stats_str = "\n".join([f"{stat['player_name']} - Dificultad: {stat['difficulty']}, Movimientos: {stat['move_count']}, Tiempo: {stat['time_elapsed']}" for stat in stats_list])
        messagebox.showinfo("Ranking de Jugadores", stats_str)

    def on_card_click(self, x, y):
        """Manejo de evento cuando el jugador hace clic en una carta."""
        result = self.game_model.process_card_click(x, y)
        if result == "hide_cards":
            self.root.after(1000, self.hide_cards)
        elif result == "game_over":
            self.game_model.save_stats()
            self.game_view.stop_timer()
            messagebox.showinfo("¡Juego Terminado!", f"¡Felicidades {self.game_model.player_name}, has completado el juego en {self.game_model.time_elapsed} segundos con {self.game_model.move_count} movimientos!")
            self.quit_game()
        self.game_view.update_board()
        self.game_view.update_move_count(self.game_model.move_count)

    def hide_cards(self):
        """Oculta las cartas después de un retraso."""
        self.game_model.hide_cards()
        self.game_view.update_board()

    def quit_game(self):
        """Cierra el juego."""
        self.root.quit()