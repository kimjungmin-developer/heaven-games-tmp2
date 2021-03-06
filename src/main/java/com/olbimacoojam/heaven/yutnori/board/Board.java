package com.olbimacoojam.heaven.yutnori.board;

import com.olbimacoojam.heaven.yutnori.Color;
import com.olbimacoojam.heaven.yutnori.MoveVerifier;
import com.olbimacoojam.heaven.yutnori.piece.Piece;
import com.olbimacoojam.heaven.yutnori.piece.moveresult.MoveResult;
import com.olbimacoojam.heaven.yutnori.piece.moveresult.MoveResults;
import com.olbimacoojam.heaven.yutnori.yut.Yut;

import java.util.List;
import java.util.stream.Collectors;

public class Board {
    private final List<Piece> pieces;

    public Board(List<Piece> pieces) {
        this.pieces = pieces;
    }

    public MoveResults move(MoveVerifier moveVerifier, Yut yut) {
        List<Piece> movablePieces = moveVerifier.findMovablePieces(pieces);
        MoveResults moveResults = movePieces(movablePieces, yut);

        List<Piece> caughtPieces = findCaughtPieces(moveResults);
        MoveResults caughtMoveResults = moveCaughtPieces(caughtPieces);

        return moveResults.add(caughtMoveResults);
    }

    private MoveResults movePieces(List<Piece> movablePieces, Yut yut) {
        List<MoveResult> moveResults = movablePieces.stream()
                .map(piece -> piece.move(yut))
                .collect(Collectors.toList());

        return new MoveResults(moveResults);
    }

    private MoveResults moveCaughtPieces(List<Piece> caughtPieces) {
        return new MoveResults(caughtPieces.stream()
                .map(Piece::goBackToStandBy)
                .collect(Collectors.toList()));
    }

    private List<Piece> findCaughtPieces(MoveResults moveResults) {
        return pieces.stream()
                .filter(moveResults::isCaught)
                .collect(Collectors.toList());
    }

    public boolean isComplete(Color color) {
        return pieces.stream()
                .filter(piece -> piece.isColor(color))
                .allMatch(Piece::isComplete);
    }
}