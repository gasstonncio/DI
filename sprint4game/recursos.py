import requests
from PIL import Image, ImageTk
import io


class Recursos:
    def __init__(self, root):
        self.root = root
        self.card_images = {}
        self.load_images()

    def load_images(self):
        """Carga las imágenes necesarias para el juego."""
        try:
            # Lista de URLs de las imágenes
            urls = [
                "https://raw.githubusercontent.com/gasstonncio/DI/refs/heads/main/sprint4game/imagenes/carta_1.png",
                "https://raw.githubusercontent.com/gasstonncio/DI/refs/heads/main/sprint4game/imagenes/carta_2.png",
                "https://raw.githubusercontent.com/gasstonncio/DI/refs/heads/main/sprint4game/imagenes/carta_3.png",
                "https://raw.githubusercontent.com/gasstonncio/DI/refs/heads/main/sprint4game/imagenes/carta_4.png",
                "https://raw.githubusercontent.com/gasstonncio/DI/refs/heads/main/sprint4game/imagenes/carta_5.png",
                "https://raw.githubusercontent.com/gasstonncio/DI/refs/heads/main/sprint4game/imagenes/carta_6.png",
                "https://raw.githubusercontent.com/gasstonncio/DI/refs/heads/main/sprint4game/imagenes/carta_7.png",
                "https://raw.githubusercontent.com/gasstonncio/DI/refs/heads/main/sprint4game/imagenes/carta_8.png",
                "https://raw.githubusercontent.com/gasstonncio/DI/refs/heads/main/sprint4game/imagenes/carta_9.png",
                "https://raw.githubusercontent.com/gasstonncio/DI/refs/heads/main/sprint4game/imagenes/carta_10.png",
                "https://raw.githubusercontent.com/gasstonncio/DI/refs/heads/main/sprint4game/imagenes/carta_11.png",
                "https://raw.githubusercontent.com/gasstonncio/DI/refs/heads/main/sprint4game/imagenes/carta_12.png",
                "https://raw.githubusercontent.com/gasstonncio/DI/refs/heads/main/sprint4game/imagenes/carta_13.png",
                "https://raw.githubusercontent.com/gasstonncio/DI/refs/heads/main/sprint4game/imagenes/carta_14.png",
                "https://raw.githubusercontent.com/gasstonncio/DI/refs/heads/main/sprint4game/imagenes/carta_15.png",
                "https://raw.githubusercontent.com/gasstonncio/DI/refs/heads/main/sprint4game/imagenes/carta_16.png",
                "https://raw.githubusercontent.com/gasstonncio/DI/refs/heads/main/sprint4game/imagenes/carta_17.png",
                "https://raw.githubusercontent.com/gasstonncio/DI/refs/heads/main/sprint4game/imagenes/carta_18.png",
                "https://raw.githubusercontent.com/gasstonncio/DI/refs/heads/main/sprint4game/imagenes/carta_19.png",
                "https://raw.githubusercontent.com/gasstonncio/DI/refs/heads/main/sprint4game/imagenes/carta_20.png",
                "https://raw.githubusercontent.com/gasstonncio/DI/refs/heads/main/sprint4game/imagenes/carta_21.png",
                "https://raw.githubusercontent.com/gasstonncio/DI/refs/heads/main/sprint4game/imagenes/carta_22.png",
                "https://raw.githubusercontent.com/gasstonncio/DI/refs/heads/main/sprint4game/imagenes/carta_23.png",
                "https://raw.githubusercontent.com/gasstonncio/DI/refs/heads/main/sprint4game/imagenes/carta_24.png",
                "https://raw.githubusercontent.com/gasstonncio/DI/refs/heads/main/sprint4game/imagenes/carta_25.png",
                "https://raw.githubusercontent.com/gasstonncio/DI/refs/heads/main/sprint4game/imagenes/carta_26.png",
                "https://raw.githubusercontent.com/gasstonncio/DI/refs/heads/main/sprint4game/imagenes/carta_27.png",
                "https://raw.githubusercontent.com/gasstonncio/DI/refs/heads/main/sprint4game/imagenes/carta_28.png",
                "https://raw.githubusercontent.com/gasstonncio/DI/refs/heads/main/sprint4game/imagenes/carta_29.png",
                "https://raw.githubusercontent.com/gasstonncio/DI/refs/heads/main/sprint4game/imagenes/carta_30.png",
                "https://raw.githubusercontent.com/gasstonncio/DI/refs/heads/main/sprint4game/imagenes/carta_31.png",
                "https://raw.githubusercontent.com/gasstonncio/DI/refs/heads/main/sprint4game/imagenes/carta_32.png",
            ]
            back_url = "https://raw.githubusercontent.com/gasstonncio/DI/refs/heads/main/sprint4game/uno_card_back.png"

            self.back_image = self.load_image(back_url)  # Carga la imagen de la parte posterior de las cartas

            for i, url in enumerate(urls, 1):
                self.card_images[f"C{i}"] = self.load_image(url)

        except Exception as e:
            print(f"Error al descargar o procesar las imágenes: {e}")

    def load_image(self, url):
        """Descarga y carga una imagen desde una URL."""
        try:
            response = requests.get(url)
            image = Image.open(io.BytesIO(response.content))
            image = image.resize((100, 150), Image.ANTIALIAS)
            return ImageTk.PhotoImage(image)
        except Exception as e:
            print(f"Error al cargar la imagen desde {url}: {e}")
            return None

    def get_card_image(self, card_value):
        """Devuelve la imagen de una carta dada su valor."""
        return self.card_images.get(card_value, self.back_image)

    def get_back_image(self):
        """Devuelve la imagen de la parte posterior de la carta."""
        return self.back_image

