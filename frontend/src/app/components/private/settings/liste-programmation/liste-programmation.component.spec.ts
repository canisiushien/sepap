import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListeProgrammationComponent } from './liste-programmation.component';

describe('ListeProgrammationComponent', () => {
  let component: ListeProgrammationComponent;
  let fixture: ComponentFixture<ListeProgrammationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ListeProgrammationComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ListeProgrammationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
