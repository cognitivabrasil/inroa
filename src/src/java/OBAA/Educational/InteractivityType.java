/*
 * OBAA - Agent Based Leanring Objetcs
 *
 * This file is part of Obaa.
 * Obaa is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Obaa is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Obaa. If not, see <http://www.gnu.org/licenses/>.
 */

package OBAA.Educational;

/**
 * <div class="en">
 *
 * Predominant mode of learning supported by this learning object.
 *
 * "Active" learning (e.g., learning by doing) is supported by content that
 * directly induces productive action by the learner. An active learning object
 * prompts the learner for semantically meaningful input or for some other kind
 * of productive action or decision, not necessarily performed within the
 * learning object's framework. Active documents include simulations,
 * questionnaires, and exercises.
 *
 * "Expositive" learning (e.g., passive learning) occurs when the learner's job
 * mainly consists of absorbing the content exposed to him (generally through
 * text, images or sound). An expositive learning object displays information
 * but does not prompt the learner for any semantically meaningful input.
 * Expositive documents include essays, video clips, all kinds of graphical
 * material, and hypertext documents. When a learning object blends the active
 * and expositive interactivity types, then its interactivity type is "mixed".
 *
 * NOTE:--Activating links to navigate in hypertext documents is not considered
 * to be a productive action.
 *
 * Value Space: active, expositive or mixed
 *
 * Example:
 * active documents (with learner's action):
 * - simulation (manipulates, controls or enters data or parameters);
 * - questionnaire (chooses or writes answers);
 * - exercise (finds solution);
 * -  problem statement (writes solution).
 *
 * expositive documents (with learner's action):
 * - hypertext document (reads, navigates);
 * - video (views, rewinds, starts, stops);
 * - graphical material (views);
 * - audio material (listens, rewinds, starts, stops).
 *
 * mixed document:
 * - hypermedia document with embedded simulation applet.
 *
 * according to IEEE LOM http://ltsc.ieee.org/
 *
 *</div>
 *
 * <div class="br">
 * 
 *
 * Adaptado de http://www.portalobaa.org
 *</div>
 *
 * @author LuizRossi
 */

public class InteractivityType {
private String interactiveType;

    public InteractivityType() {
        interactiveType = "";
    }

    public InteractivityType(String interactiveType) {
        this.interactiveType = interactiveType;
    }

    public void setInteractiveType(String interactiveType) {
        this.interactiveType = interactiveType;
    }

    public String getInteractiveType() {
        return interactiveType;
    }
}
