from typing import Optional
from datetime import datetime
from pydantic import BaseModel


class Chat(BaseModel):
    chatId: Optional[int]
    userId: Optional[int]
    uuid: str
    sender: str
    message: str
    regDate: Optional[datetime]
    chatStatus: Optional[str]
