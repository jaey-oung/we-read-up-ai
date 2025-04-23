from chat.chat import router as chat_router
from fastapi import FastAPI

app = FastAPI()

app.include_router(chat_router)

if __name__ == "__main__":
    import uvicorn
    uvicorn.run("main:app", host="127.0.0.1", port=8000, reload=True)