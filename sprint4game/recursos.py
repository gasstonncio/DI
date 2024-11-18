import requests
from PIL import Image, ImageTk
from io import BytesIO

class Recursos:
    def __init__(self):
        self.carta_boca_abajo = None
        self.cartas = {}
        self.load_images()

    def load_images(self):
        """
        Carga las imágenes para las cartas desde URLs y la imagen de carta boca abajo.
        Las URLs de las cartas deben ser proporcionadas.
        """
        # URL de la carta boca abajo (la imagen que se usa cuando la carta está boca abajo)
        self.carta_boca_abajo = self.download_image("https://raw.githubusercontent.com/gasstonncio/DI/refs/heads/main/sprint4game/uno_card_back.png")

        # URLs de las cartas de juego (cambia estas URLs por las de tus cartas)
        for i in range(1, 33):  # Suponiendo que tienes hasta 32 cartas
            image_url = f"https://example.com/carta_{i}.png"
            self.cartas[f"C{i}"] = self.download_image(image_url)

    def download_image(self, url):
        """
        Descarga una imagen desde una URL y la convierte en un objeto PhotoImage de Tkinter.
        """
        try:
            response = requests.get(url)
            response.raise_for_status()  # Verifica si hubo errores en la descarga
            img = Image.open(BytesIO(response.content))
            img = img.resize((100, 100), Image.ANTIALIAS)  # Ajusta el tamaño de la imagen
            return ImageTk.PhotoImage(img)
        except Exception as e:
            print(f"Error al descargar o procesar la imagen desde {url}: {e}")
            return None

    def get_card_image(self, card_value):
        """
        Devuelve la imagen correspondiente al valor de la carta (por ejemplo, C1, C2...).
        Si no encuentra la imagen, devuelve la carta boca abajo.
        """
        return self.cartas.get(card_value, self.carta_boca_abajo)

    def get_back_image(self):
        """
        Devuelve la imagen para la carta boca abajo.
        """
        return self.carta_boca_abajo

